package org.monkey.msd.security.config;

import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.service.impl.MsdUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * ProjectConfig
 *
 * @author cc
 * @since 2024/12/13 16:10
 */
@Configuration
@Slf4j
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private MsdUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic(); mode_inheritablethreadlocal
//        http.authorizeRequests()
//                .anyRequest().authenticated(); //所有请求都需要身份验证
        log.info("1111111");
        http.httpBasic();
        http.csrf().disable().authorizeRequests()
                .antMatchers("/hello/hello").permitAll()
                .anyRequest().authenticated(); //所有请求都需要身份验证
//        http.httpBasic();
//        http.authorizeRequests()
//                .antMatchers("/create").permitAll()
//                .anyRequest().permitAll(); //所有请求都不需要身份验证
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("2222222");
        auth.authenticationProvider(customAuthenticationProvider);
//        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("3333333");
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    //    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        //User类用于创建代表用户的对象的构造器实现，该类由SpringSecurity提供，并非我们自己创建
//        UserDetails user = User.withUsername("msd")
//                .password("123456")
//                .authorities("read")
//                .build();
//        userDetailsService.createUser(user);
//        return userDetailsService;
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    /*
     * 注解 @EnableAsync 和 @Async注解实现异步时 需要配置这个bean
     */
//    @Bean
//    public InitializingBean initializingBean(){
//        return ()-> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }

}
