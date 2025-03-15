package org.monkey.msd.security02.config;

import lombok.RequiredArgsConstructor;
import org.monkey.msd.security02.handler.CommonLoginFailureHandler;
import org.monkey.msd.security02.handler.CommonLoginSuccessHandler;
import org.monkey.msd.security02.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;

/**
 * SpringSecurityConfig
 *
 * @author cc
 * @since 2025/2/7 15:08
 */
@Configuration
@EnableAsync
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl commonUserDetailServiceImpl;

    @Autowired
    private CommonLoginSuccessHandler successHandler;

    @Autowired
    private CommonLoginFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
//                .logout().and()
//                .formLogin()
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .and().httpBasic().and()
//                .authorizeRequests()
//                .antMatchers("/home","/toLogin","/login", "/user/create", "/user/remove", "/kaptcha", "/smsCode", "/smslogin").permitAll()
//                .mvcMatchers("/hello").hasRole("ADMIN")
//                .mvcMatchers("/xiao").hasRole("USER");
                .httpBasic().and().authorizeRequests()
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(commonUserDetailServiceImpl)
                .passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        HashMap<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
