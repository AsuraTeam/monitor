/**
 * @FileName: Application.java
 * @Package: com.asura.agent
 * @author zhaozq14
 * @created 2016/08/13  06:28 AM
 * <p/>
 *
 */
package com.asura.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * <p>Spring boot 启动入口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class Application  {


    private Logger logger = LoggerFactory.getLogger(Application.class);


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CharacterEncodingFilter characterEncodingFilter() {
        logger.info("CharacterEncodingFilter :{}", "utf-8");
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }





    /**
     * 入口main方法
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
