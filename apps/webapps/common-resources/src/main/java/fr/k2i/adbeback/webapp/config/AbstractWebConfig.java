package fr.k2i.adbeback.webapp.config;

import fr.k2i.adbeback.application.services.mail.config.EmailConfig;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.boot.autoconfigure.jdbc.TomcatDataSourceConfiguration;
import org.springframework.boot.context.embedded.MultiPartConfigFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.servlet.MultipartConfigElement;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 14/12/13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
@ImportResource("classpath*:spring-webflow.xml")
public abstract class AbstractWebConfig {

    @Bean
    public TomcatDataSourceConfiguration dataSourceConfiguration(){
        return new TomcatDataSourceConfiguration();
    }

    @Bean
    public EmailConfig emailConfig(){
        return new EmailConfig();
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("resources");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public Validator validator(){
        return new LocalValidatorFactoryBean();
    }


    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultiPartConfigFactory factory = new MultiPartConfigFactory();
        factory.setMaxFileSize("15MB");
        factory.setMaxRequestSize("25MB");
        return factory.createMultipartConfig();
    }

}
