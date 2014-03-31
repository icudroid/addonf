/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.k2i.adbeback;


import fr.k2i.adbeback.webapp.config.AbstractWebConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan(basePackages = "fr.k2i.adbeback")
//@PropertySource(value = {"classpath:application.properties","classpath:mail.properties"})
public class WebConfig extends AbstractWebConfig {

    @Bean
    public SecurityConfig securityConfig() {
        return new SecurityConfig();
    }

    @Bean
    public MvcConfig mvcConfig() {
        return new MvcConfig();
    }


    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(WebConfig.class).run(args);
    }
}