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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext context;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }

    public UserDetailsService webUserServiceDetail(){
        return (UserDetailsService) context.getBean("webUserDao");
    }

    @Bean
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.ObjectPostProcessor<Object> objectPostProcessor) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder,UserDetailsService> builder = new AuthenticationManagerBuilder(objectPostProcessor).userDetailsService(webUserServiceDetail());
        builder.passwordEncoder(passwordEncoder());

        return builder.and().build();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/home.html").permitAll()

                .antMatchers("/signup.html").permitAll()
                .antMatchers("/confirmEnroll/**").permitAll()
                .antMatchers("/getTowns/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/getForgottenPwd").permitAll()
                .antMatchers("/pwdinit/**").permitAll()



                .antMatchers("/logout-success").permitAll()
                .antMatchers("/lib/**").permitAll()
                .antMatchers("/coral/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/template/**").permitAll()

                .anyRequest().authenticated();

/*                .antMatchers("/custom-logout").hasRole("ANNONCEUR")
                .antMatchers("*//**").hasRole("ANNONCEUR");*/



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

/*        http.rememberMe()
                .tokenValiditySeconds(60*60*24)
                .key("__0n3K3y!!")
                .useSecureCookie(true)
                .userDetailsService(webUserServiceDetail());*/
    }




}