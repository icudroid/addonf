package fr.k2i.adbeback.webapp.config;

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
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.ObjectPostProcessor<java.lang.Object> objectPostProcessor) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder,UserDetailsService> builder = new AuthenticationManagerBuilder(objectPostProcessor).userDetailsService((UserDetailsService) context.getBean("playerDao"));
        builder.passwordEncoder(passwordEncoder());

        return builder.and().build();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/home.html").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/logout-success").permitAll()
                    .antMatchers("/lib/**").permitAll()
                    .antMatchers("/components/**").permitAll()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/img/**").permitAll()
                    .antMatchers("/template/**").permitAll()
                    .antMatchers("/checkout.html").permitAll()
                    .antMatchers("/cart.html").permitAll()
                    .antMatchers("/custom-logout").hasRole("USER")


                //manage cart
                .antMatchers("/rest/addToCart/*").permitAll()
                .antMatchers("/rest/removeFromCart/*").permitAll()
                .antMatchers("/rest/cart").permitAll()
                .antMatchers("/rest/cart/empty").permitAll()
                .antMatchers("/partial/cart.html").permitAll()


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

