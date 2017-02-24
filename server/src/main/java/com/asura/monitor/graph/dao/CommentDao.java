package com.asura.monitor.graph.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by admin on 2016/8/10.
 */
public class CommentDao {

    /**
     *
     * @param mybatisDaoContext
     * @return
     */
    public static MybatisDaoContext setDao(MybatisDaoContext mybatisDaoContext){
        if(mybatisDaoContext==null){
            mybatisDaoContext = new MybatisDaoContext();
            ClassPathXmlApplicationContext cxf = new ClassPathXmlApplicationContext("application-context.xml","application-datasource.xml");
            SqlSessionTemplate template = (SqlSessionTemplate) cxf.getBean("monitor.writeSqlSessionTemplate.M");
            mybatisDaoContext.setReadSqlSessionTemplate(template);
        }
        return mybatisDaoContext;
    }

}
