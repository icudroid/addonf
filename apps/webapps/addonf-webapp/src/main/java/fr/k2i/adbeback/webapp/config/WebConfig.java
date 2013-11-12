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

package fr.k2i.adbeback.webapp.config;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "fr.k2i.adbeback")
@PropertySource(value = "classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApplicationContext context;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.driverClassName}")
    private String driverClassName;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.maxActive:100}")
    private Integer maxActive;

    @Value("${datasource.maxWait:1000}")
    private Integer maxWait;

    @Value("${datasource.poolPreparedStatements:true}")
    private Boolean poolPreparedStatements;

    @Value("${datasource.defaultAutoCommit:true}")
    private Boolean defaultAutoCommit;


    /**
     *
     <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
     <property name="driverClassName" value="${jdbc.driverClassName}"/>
     <property name="url" value="${jdbc.url}"/>
     <property name="username" value="${jdbc.username}"/>
     <property name="password" value="${jdbc.password}"/>
     <property name="maxActive" value="100"/>
     <property name="maxWait" value="1000"/>
     <property name="poolPreparedStatements" value="true"/>
     <property name="defaultAutoCommit" value="true"/>
     </bean>
     * @return
     */

    @Bean
    public DataSource  dataSource(){
        org.apache.commons.dbcp.BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setDefaultAutoCommit(defaultAutoCommit);
        return dataSource;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.ObjectPostProcessor<java.lang.Object> objectPostProcessor) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder,UserDetailsService> builder = new AuthenticationManagerBuilder(objectPostProcessor).userDetailsService((UserDetailsService) context.getBean("playerDao"));
        builder.passwordEncoder(passwordEncoder());

        return builder.and().build();

    }


    @Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

    @Order(Ordered.LOWEST_PRECEDENCE - 8)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/home.html").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/lib/**").permitAll()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/**").hasRole("USER")
                    //.fullyAuthenticated()
                    .and()
                        .formLogin()
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll()
                    .and()
                        .logout()
                        .deleteCookies("remove")
                        .invalidateHttpSession(false)
                        .logoutUrl("/custom-logout")
                        .logoutSuccessUrl("/logout-success")
                    .and()
                        .sessionManagement()
                            .maximumSessions(1)
                            .expiredUrl("/login?expired");
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout-success").setViewName("logout");
    }



    public static void main(String[] args) throws Exception {
        // Set user password to "password" for demo purposes only
        new SpringApplicationBuilder(WebConfig.class).properties(
                "security.basic.enabled=false").run(args);
    }
}
