package org.monkey.msd.cloud.oauth2.server.config;

import org.monkey.msd.cloud.oauth2.server.provider.ServerHttpBearerAuthenticationConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

/**
 * SecurityConfig
 *
 * @author cc
 * @since 2025/4/3 11:55
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/static/**").permitAll() // 放行登录页和静态资源
                        .requestMatchers("/oauth2/**").permitAll()  // 开放 OAuth2 端点
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")          // 指定自定义登录页路径
                        .loginProcessingUrl("/login") // 表单提交地址（默认即可）
                        .defaultSuccessUrl("/home")   // 登录成功后跳转路径
                        .failureUrl("/login?error")   // 登录失败后跳转路径
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")        // 注销地址
                        .logoutSuccessUrl("/login?logout")
                )
                .csrf(AbstractHttpConfigurer::disable);;
        return http.build();
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
