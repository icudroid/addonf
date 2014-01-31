package fr.k2i.adbeback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext context;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.ObjectPostProcessor<Object> objectPostProcessor) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder,UserDetailsService> builder = new AuthenticationManagerBuilder(objectPostProcessor).userDetailsService((UserDetailsService) context.getBean("webAdminDao"));
        builder.passwordEncoder(passwordEncoder());

        return builder.and().build();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/logout-success").permitAll()
                    .antMatchers("/coral/**").permitAll()
                    .antMatchers("/lib/**").permitAll()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/template/**").permitAll()
                    .antMatchers("/custom-logout").hasRole("ADMIN")
                    .antMatchers("/manage/**").hasRole("ADMIN")
                    .antMatchers("/**").hasRole("ADMIN");


        http.formLogin()
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll();

        http.logout()
                            .deleteCookies("remove")
                            .invalidateHttpSession(false)
                            .logoutUrl("/custom-logout")
                            .logoutSuccessUrl("/logout-success");

        http.sessionManagement()
                            .maximumSessions(1)
                            .expiredUrl("/login?expired");
    }

}