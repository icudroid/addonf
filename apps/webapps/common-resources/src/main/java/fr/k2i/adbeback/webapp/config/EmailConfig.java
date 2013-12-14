package fr.k2i.adbeback.webapp.config;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.MailEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 19/11/13
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class EmailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.defaultEncoding:UTF-8}")
    private String defaultEncoding;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.default.sender}")
    private String defaultFrom;

    @Value("${email.resources.uri}/images")
    private String imagesResources;

    @Bean
    public org.springframework.mail.javamail.JavaMailSenderImpl mailSender(){
        org.springframework.mail.javamail.JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setProtocol(protocol);
        sender.setDefaultEncoding(defaultEncoding);
        sender.setUsername(username);
        sender.setPassword(password);
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth",auth);
        sender.setJavaMailProperties(props);
        return sender;
    }


    @Bean
    public IMailEngine mailEngine(){
        IMailEngine mailEngine = new MailEngine(mailSender(),defaultFrom,freeMarkerConfigurer(),imagesResources);
        return mailEngine;
    }



    @Bean
    public org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates/email");
        Properties props = new Properties();
        props.setProperty("url_escaping_charset","UTF-8");
        props.setProperty("output_encoding","UTF-8");
        props.setProperty("tag_syntax","square_bracket");
        freeMarkerConfigurer.setFreemarkerSettings(props);

        return freeMarkerConfigurer;
       }

}
