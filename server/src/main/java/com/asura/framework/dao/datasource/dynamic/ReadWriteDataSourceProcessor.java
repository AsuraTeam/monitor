package com.asura.framework.dao.datasource.dynamic;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;


/**
 * 
 * 
 * <pre>
 * 
 * 此类实现了两个职责（为了减少类的数量将两个功能合并到一起了）：
 *   读/写动态数据库选择处理器
 *   通过AOP切面实现读/写选择
 *   
 *   
 * ★★读/写动态数据库选择处理器★★
 * 1、首先读取<tx:advice>事务属性配置
 * 
 * 2、对于所有读方法设置 read-only="true" 表示读取操作（以此来判断是选择读还是写库），其他操作都是走写库
 *    如<tx:method name="×××" read-only="true"/>
 *    
 * 3、 forceChoiceReadOnWrite用于确定在如果目前是写（即开启了事务），下一步如果是读，
 *    是直接参与到写库进行读，还是强制从读库读<br/>
 *      forceChoiceReadOnWrite:true 表示目前是写，下一步如果是读，强制参与到写事务（即从写库读）
 *                                  这样可以避免写的时候从读库读不到数据
 *                                  
 *                                  通过设置事务传播行为：SUPPORTS实现
 *                                  
 *      forceChoiceReadOnWrite:false 表示不管当前事务是写/读，都强制从读库获取数据
 *                                  通过设置事务传播行为：NOT_SUPPORTS实现（连接是尽快释放）                
 *                                  『此处借助了 NOT_SUPPORTS会挂起之前的事务进行操作 然后再恢复之前事务完成的』
 * 4、配置方式
 *  <bean id="readWriteDataSourceTransactionProcessor" class="cn.javass.common.datasource.ReadWriteDataSourceProcessor">
 *      <property name="forceChoiceReadWhenWrite" value="false"/>
 *  </bean>
 *
 * 5、目前只适用于<tx:advice>情况 TODO 支持@Transactional注解事务
 *  
 *  
 *  
 * ★★通过AOP切面实现读/写库选择★★
 * 
 * 1、首先将当前方法 与 根据之前【读/写动态数据库选择处理器】  提取的读库方法 进行匹配
 * 
 * 2、如果匹配，说明是读取数据：
 *  2.1、如果forceChoiceReadOnWrite:true，即强制走读库
 *  2.2、如果之前是写操作且forceChoiceReadOnWrite:false，将从写库进行读取
 *  2.3、否则，到读库进行读取数据
 * 
 * 3、如果不匹配，说明默认将使用写库进行操作
 * 
 * 4、配置方式
 *      <aop:aspect order="-2147483648" ref="readWriteDataSourceTransactionProcessor">
 *          <aop:around pointcut-ref="txPointcut" method="determineReadOrWriteDB"/>
 *      </aop:aspect>
 *  4.1、此处order = Integer.MIN_VALUE 即最高的优先级（请参考http://jinnianshilongnian.iteye.com/blog/1423489）
 *  4.2、切入点：txPointcut 和 实施事务的切入点一样
 *  4.3、determineReadOrWriteDB方法用于决策是走读/写库的，请参考
 *       @see cn.javass.common.datasource.ReadWriteDataSourceDecision
 *       @see cn.javass.common.datasource.ReadWriteDataSource
 * 
 * </pre>
 * @author guohm
 *
 */
public class ReadWriteDataSourceProcessor implements BeanPostProcessor {
	
	
    private boolean forceChoiceReadWhenWrite = false;
    
    private Map<String, Boolean> readMethodMap = new HashMap<String, Boolean>();

    /**
     * 当之前操作是写的时候，是否强制从从库读
     * 默认（false） 当之前操作是写，默认强制从写库读
     * @param forceReadOnWrite
     */
    
    public void setForceChoiceReadWhenWrite(boolean forceChoiceReadWhenWrite) {
        
        this.forceChoiceReadWhenWrite = forceChoiceReadWhenWrite;
    }
    

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(!(bean instanceof NameMatchTransactionAttributeSource)) {
            return bean;
        }
        
        try {
            NameMatchTransactionAttributeSource transactionAttributeSource = (NameMatchTransactionAttributeSource)bean;
            Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
            nameMapField.setAccessible(true);
            @SuppressWarnings("unchecked")
			Map<String, TransactionAttribute> nameMap = (Map<String, TransactionAttribute>) nameMapField.get(transactionAttributeSource);
            
            for(Entry<String, TransactionAttribute> entry : nameMap.entrySet()) {
                RuleBasedTransactionAttribute attr = (RuleBasedTransactionAttribute)entry.getValue();

                //仅对read-only的处理
                if(!attr.isReadOnly()) {
                    continue;
                }
                
                String methodName = entry.getKey();
                Boolean isForceChoiceRead = Boolean.FALSE;
                if(forceChoiceReadWhenWrite) {
                    //不管之前操作是写，默认强制从读库读 （设置为NOT_SUPPORTED即可）
                    //NOT_SUPPORTED会挂起之前的事务
                    attr.setPropagationBehavior(Propagation.NOT_SUPPORTED.value());
                    isForceChoiceRead = Boolean.TRUE;
                } else {
                    //否则 设置为SUPPORTS（这样可以参与到写事务）
                    attr.setPropagationBehavior(Propagation.SUPPORTS.value());
                }
                //logger.debug(arg0);.debug("read/write transaction process  method:{} force read:{}", methodName, isForceChoiceRead);
                readMethodMap.put(methodName, isForceChoiceRead);
            }
            
        } catch (Exception e) {
            throw new ReadWriteDataSourceTransactionException("process read/write transaction error", e);
        }
        
        return bean;
    }
    
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private class ReadWriteDataSourceTransactionException extends NestedRuntimeException {
        /**
		 * 
		 */
		private static final long serialVersionUID = -2141964601977907624L;

		public ReadWriteDataSourceTransactionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    
    
    
    
    public Object determineReadOrWriteDB(ProceedingJoinPoint pjp) throws Throwable {
    	boolean isStart = ReadWriteDataSourceDecision.isStart();
        if (isChoiceReadDB(pjp.getSignature().getName())) {
            ReadWriteDataSourceDecision.markRead();
        } else {
            ReadWriteDataSourceDecision.markWrite();
        }
            
        try {
            return pjp.proceed();
        }finally {
            ReadWriteDataSourceDecision.reset();
            if(isStart){
            	ReadWriteDataSourceDecision.clean();
            }
        }
        
        
    }
    
    private boolean isChoiceReadDB(String methodName) {

        String bestNameMatch = null;
        for (String mappedName : this.readMethodMap.keySet()) {
            if (isMatch(methodName, mappedName)) {
                bestNameMatch = mappedName;
                break;
            }
        }

        Boolean isForceChoiceRead = readMethodMap.get(bestNameMatch);
        //表示强制选择 读 库
        if(isForceChoiceRead == Boolean.TRUE) {
            return true;
        }
        
        //如果之前选择了写库 现在还选择 写库
        if(ReadWriteDataSourceDecision.isChoiceWrite()) {
            return false;
        }
        
        //表示应该选择读库
        if(isForceChoiceRead != null) {
            return true;
        }
        //默认选择 写库
        return false;
    }


    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

}

