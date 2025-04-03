package org.monkey.msd.cloud.oauth2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 *
 * @author cc
 * @since 2025/4/2 9:24
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

//    @Autowired
//    private GithubClient githubClient;
    @Autowired
    private GiteeClient giteeClient;
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .anyRequest().authenticated().and()
                .oauth2Login(c->c.clientRegistrationRepository(clientRegistrationRepository()))
                .build();
    }

    private ClientRegistrationRepository clientRegistrationRepository(){
        return new InMemoryClientRegistrationRepository(giteeClient.getClientRegistration());
//        return new InMemoryClientRegistrationRepository(githubClient.getClientRegistration());
    }
}
