package fr.k2i.adbeback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
@EnableWebMvcSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 8)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext context;


    @Value(value ="${addonf.static.url}" )
    private String staticUrl;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService((UserDetailsService) context.getBean("webPlayerDao"));
        UserDetailsService userDetailsService = (UserDetailsService) context.getBean("webPlayerDao");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public AnonymousAuthenticationFilter anonymousAuthFilter() {
        return new AnonymousAuthenticationFilter("anonymous"){
            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException {

                //if (applyAnonymousForThisRequest((HttpServletRequest) req)) {

                    addAttributesToSession((HttpServletRequest)req);

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(createAuthentication((HttpServletRequest) req));

                        if (logger.isDebugEnabled()) {
                            logger.debug("Populated SecurityContextHolder with anonymous token: '"
                                    + SecurityContextHolder.getContext().getAuthentication() + "'");
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("SecurityContextHolder not populated with anonymous token, as it already contained: '"
                                    + SecurityContextHolder.getContext().getAuthentication() + "'");
                        }
                    }
                //}

                chain.doFilter(req, res);
            }

            private void addAttributesToSession(HttpServletRequest req) {
                if(req.getSession().getAttribute("staticUrl")==null)
                    req.getSession().setAttribute("staticUrl", staticUrl);
            }

        };
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/signup.html").permitAll()
                .antMatchers("/getTownsByZipcode/**").permitAll()
                .antMatchers("/enrollConfirm").permitAll()
                .antMatchers("/game").permitAll()
                .antMatchers("/resume").permitAll()
                .antMatchers("/partials/**").permitAll()
                .antMatchers("/rest/createGame").permitAll()

                .antMatchers("/logo/**").permitAll()
                .antMatchers("/video/**").permitAll()
                .antMatchers("/rest/play/**").permitAll()
                .antMatchers("/rest/noresponse/*").permitAll()
                .antMatchers("/rest/getResultAdGame").permitAll()

                .antMatchers("/adPay").permitAll()

                .antMatchers("/**").hasRole("USER");

        http.csrf().disable();

        http.formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .defaultSuccessUrl("/home.html")
                    .permitAll();

        http.logout()
                    .deleteCookies("remove")
                    .invalidateHttpSession(false)
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/");

        http.sessionManagement()
                    .maximumSessions(1)
                    .expiredUrl("/");

        http.anonymous()
                .authenticationFilter(anonymousAuthFilter());

//autorisé les iframes
/*        http
                .headers()
                .frameOptions().disable();*/
    }



}

