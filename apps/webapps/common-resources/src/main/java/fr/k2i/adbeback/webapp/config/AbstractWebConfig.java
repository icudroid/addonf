package fr.k2i.adbeback.webapp.config;

import fr.k2i.adbeback.application.services.mail.config.EmailConfig;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 14/12/13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractWebConfig extends WebMvcConfigurerAdapter {


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
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }


}
