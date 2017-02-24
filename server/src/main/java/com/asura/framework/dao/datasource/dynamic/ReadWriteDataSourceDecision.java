package com.asura.framework.dao.datasource.dynamic;

import java.util.Stack;


/**
 * <pre>
 * 读/写动态数据库 决策者
 * 根据DataSourceType是write/read 来决定是使用读/写数据库
 * 通过ThreadLocal绑定实现选择功能
 * </pre>
 * @author guohm
 *
 */
public class ReadWriteDataSourceDecision {
    
    public enum DataSourceType {
        write, read;
    }
    
    
   //private static final ThreadLocal<DataSourceType> holder = new ThreadLocal<DataSourceType>();
    private static final ThreadLocal<DataSourceStack> dsholder = new ThreadLocal<DataSourceStack>();

    public static void markWrite() {
    	if(isStart()){
    		dsholder.set(new DataSourceStack());
    	}
        //holder.set(DataSourceType.write);
    	dsholder.get().push(DataSourceType.write);
    }
    
    public static void markRead() {
    	if(isStart()){
    		dsholder.set(new DataSourceStack());
    	}
        //holder.set(DataSourceType.read);
    	dsholder.get().push(DataSourceType.read);
    }
    
    public static void setDataSourceType(DataSourceType dsType) {
    	if(isStart()){
    		dsholder.set(new DataSourceStack());
    	}
    	dsholder.get().push(dsType);
    }
    
    public static void reset() {
        //holder.set(null);
    	dsholder.get().pop();
    }
    
    public static void clean() {
        //holder.set(null);
    	dsholder.remove();
    }
    
    /**
     * 判断是否为线程开始
     * @return
     */
    public static boolean isStart() {
    	return dsholder.get() == null;
    } 
    public static boolean isChoiceNone() {
//        return null == dsholder.get().peek();
    	DataSourceStack dsStack = dsholder.get();
    	if(dsStack != null){
    		return  DataSourceType.write == dsStack.peek();
    	}
    	return false;
    }
    
    public static boolean isChoiceWrite() {
        //return DataSourceType.write == holder.get();
    	DataSourceStack dsStack = dsholder.get();
    	if(dsStack != null){
    		return  DataSourceType.write == dsStack.peek();
    	}
    	return false;
    }
    
    public static boolean isChoiceRead() {
    	
        //return DataSourceType.read == holder.get();
    	DataSourceStack dsStack = dsholder.get();
    	if(dsStack != null){
    		return  DataSourceType.read == dsStack.peek();
    	}
    	return false;
    }

    /**
     * 判断线程中当前数据源是否和给定数据源匹配
     * 
     * @param dsType
     * @return
     * @author guohm
     * @datetime 2014年11月27日 下午5:37:59
     * @since 1.0.0
     *
     */
    public static boolean isCurrentDataSource(DataSourceType dsType){
    	return dsholder.get().peek() == dsType;
    }
    static class DataSourceStack extends Stack<DataSourceType>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 6413410305035984332L;

		/* (non-Javadoc)
		 * @see java.util.Stack#pop()
		 */
		@Override
		public synchronized DataSourceType pop() {
			if(this.empty()){
				return null;
			}
			return super.pop();
		}

		/* (non-Javadoc)
		 * @see java.util.Stack#peek()
		 */
		@Override
		public synchronized DataSourceType peek() {
			if(this.empty()){
				return null;
			}
			return super.peek();
		}
    	
    }
}
