package org.monkey.msd.cloud.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.gateway.provider.JwtAuthenticationProvider;
import org.monkey.msd.cloud.gateway.provider.ServerHttpBearerAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

/**
 * SecurityConfig
 *
 * @author cc
 * @since 2025/3/24 13:53
 */
@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationWebFilter jwtAuthFilter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/api/test/index.html").permitAll()
                        .pathMatchers("/api/**").authenticated()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter(JwtAuthenticationProvider jwtProvider) {
        log.info("jwtAuthenticationWebFilter 111111111111");
        ReactiveAuthenticationManager authManager = new ReactiveAuthenticationManagerAdapter(new ProviderManager(jwtProvider));
        ServerAuthenticationConverter authConverter = new ServerHttpBearerAuthenticationConverter();

        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(authManager);
        jwtAuthFilter.setServerAuthenticationConverter(authConverter);
        jwtAuthFilter.setAuthenticationSuccessHandler((webFilterExchange, authentication) ->
                webFilterExchange.getChain().filter(webFilterExchange.getExchange())
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
        );
        return jwtAuthFilter;
    }
}
