package com.asura.util.email;

import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
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
        String[] hosts = host.split(":");
        properties.setProperty("mail.smtp.host", hosts[0]);
        if (hosts.length > 1){
            properties.setProperty("mail.smtp.port", hosts[1]);
        }
        properties.put("mail.smtp.connectiontimeout", "1000"); //
        properties.put("mail.smtp.timeout", "1000");   //
        // 是否开启密码验证
        // 获取默认的 Session 对象。
        Session session;
        if(mailEntity.isAuth()) {
            logger.info("使用密码验证发送邮件");
            properties.put("mail.smtp.auth", "true");
            properties.setProperty("mail.user", mailEntity.getUsername());
            properties.setProperty("mail.password", mailEntity.getPassword());
            if (hosts.length > 1 && hosts[1].equals("465")) {
                final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
                properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
                properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                properties.setProperty("mail.smtp.socketFactory.port", "465");
            }
            MyAuthenticator auth = new MyAuthenticator(mailEntity.getUsername(), mailEntity.getPassword());
            session = Session.getDefaultInstance(properties, auth);
        }else{
            session = Session.getDefaultInstance(properties);
        }

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

            // 抄送人
            if (mailEntity.getCc() != null){
                for(String ccUser: mailEntity.getCc().split(",")) {
                    if (ccUser != null && ccUser.length() > 3) {
                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccUser));
                    }
                }
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
//        mailEntity.setAuth(true);
//        mailEntity.setUsername("@qq.com");
//        mailEntity.setPassword("");
//        mailEntity.setHost("smtp.qq.com:465");
//        mailEntity.setReceiver("@qq.com");
//        mailEntity.setSender("@qq.com");
//        mailEntity.setMessage("");
//        mailEntity.setSubject("www报警啦");
//        sendMail(mailEntity);
//    }
}

// SMTP验证类(内部类)，继承javax.mail.Authenticator
class MyAuthenticator extends Authenticator {
    private String strUser;
    private String strPwd;

    public MyAuthenticator(String user, String password) {
        this.strUser = user;
        this.strPwd = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(strUser, strPwd);
    }
}