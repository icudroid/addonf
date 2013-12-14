package fr.k2i.adbeback.webapp.config;

import org.springframework.boot.autoconfigure.jdbc.TomcatDataSourceConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 14/12/13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractWebConfig {

    protected TomcatDataSourceConfiguration dataSourceConfiguration = new TomcatDataSourceConfiguration();

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

}
