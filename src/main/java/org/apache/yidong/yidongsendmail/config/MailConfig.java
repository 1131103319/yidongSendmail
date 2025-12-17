package org.apache.yidong.yidongsendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    // 第一个邮件发送器（主账户）
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.mail")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        // 手动设置 properties
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.connectiontimeout", "30000");
        props.put("mail.smtp.timeout", "30000");
        props.put("mail.smtp.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        return javaMailSender;
    }

    // 第二个邮件发送器
    @Bean
    @ConfigurationProperties(prefix = "spring.mail2")
    public JavaMailSender javaMailSender2() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        // 手动设置 properties
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.connectiontimeout", "30000");
        props.put("mail.smtp.timeout", "30000");
        props.put("mail.smtp.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        return javaMailSender;
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.mail3")
    public JavaMailSender javaMailSender3() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        // 手动设置 properties
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.connectiontimeout", "30000");
        props.put("mail.smtp.timeout", "30000");
        props.put("mail.smtp.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        return javaMailSender;
    }
}
