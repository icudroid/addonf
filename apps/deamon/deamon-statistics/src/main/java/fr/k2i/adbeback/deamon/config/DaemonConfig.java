package fr.k2i.adbeback.deamon.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
/*
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.adbeback.radio")
@PropertySource(value = {"classpath:application.properties"})
*/
@Configuration
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = "fr.k2i.adbeback")
@PropertySource(value = {"classpath:application.properties"})
public class DaemonConfig {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DaemonConfig.class, args);
    }
}
