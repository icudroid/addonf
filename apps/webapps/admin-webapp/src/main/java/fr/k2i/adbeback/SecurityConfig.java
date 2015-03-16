package fr.k2i.adbeback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext context;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService((UserDetailsService) context.getBean("webUserDao"));
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/coral/**").permitAll()
                    .antMatchers("/lib/**").permitAll()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/template/**").permitAll()
                    .antMatchers("/test*").permitAll()
                    .antMatchers("/manage/**").hasRole("ADMIN")
                    .antMatchers("/**").hasRole("ADMIN");


        http.formLogin()
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll();

        http.logout()
                .deleteCookies("remove")
                .invalidateHttpSession(false)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http.sessionManagement()
                            .maximumSessions(1)
                            .expiredUrl("/login?expired");
    }

}
