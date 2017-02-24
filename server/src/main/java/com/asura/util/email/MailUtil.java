package com.asura.util.email;

import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    private static final Logger logger = Logger.getLogger(MailUtil.class);

    public static boolean sendMail(MailEntity mailEntity){
        logger.info("开始发送邮件了");

        // 收件人电子邮箱
        String to = mailEntity.getReceiver();

        // 发件人电子邮箱
        String from = mailEntity.getSender();

        // 指定发送邮件的主机为 localhost
        String host =  mailEntity.getHost();

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.connectiontimeout", "1000"); //
        properties.put("mail.smtp.timeout", "1000");   //

        // 是否开启密码验证
        if(mailEntity.isAuth()) {
            properties.put("mail.smtp.auth", "true");
            properties.setProperty("mail.user", mailEntity.getUsername());
            properties.setProperty("mail.password", mailEntity.getPassword());
        }

        // 获取默认的 Session 对象。
        Session session = Session.getDefaultInstance(properties);
        try{
            // 创建默认的 MimeMessage 对象。
            MimeMessage message = new MimeMessage(session);


            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            // 多人
            for(String toUser: to.split(",")) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
            }

            // Set Subject: 头字段
            message.setSubject(mailEntity.getSubject(), "UTF-8");

            // 发送 HTML 消息, 可以插入html标签
            message.setContent(mailEntity.getMessage(),
                    "text/html;charset=utf-8" );

            // 发送消息
            Transport.send(message);
            logger.info("Sent message successfully....");
            return true;

        }catch (MessagingException mex) {
            logger.error(mex);
            return false;
        }
    }

//    public static void main(String[] args) {
//        MailEntity mailEntity = new MailEntity();
//        mailEntity.setAuth(false);
//        mailEntity.setHost("172.27.13.182");
//        mailEntity.setReceiver("zhaozq14@asura.com,270851812@qq.com");
//        mailEntity.setSender("监控系统<v@asura.com>");
//        mailEntity.setMessage("");
//        mailEntity.setSubject("www报警啦");
//        sendMail(mailEntity);
//    }
}