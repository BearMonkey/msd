package org.monkey.msd.cloud.oauth2.server.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ServerHttpBearerAuthenticationConverter
 *
 * @author cc
 * @since 2025/3/31 16:14
 */
@Component
public class ServerHttpBearerAuthenticationConverter implements ServerAuthenticationConverter {
    private static final Logger log = LoggerFactory.getLogger(ServerHttpBearerAuthenticationConverter.class);
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        log.info("ServerHttpBearerAuthenticationConverter.convert");
        return Mono.justOrEmpty(exchange.getRequest())
                .flatMap(request -> {
                    String path = request.getPath().toString();
                    // 跳过/auth/路径的认证
                    if (path.startsWith("/auth/")) {
                        return Mono.empty();
                    }
                    return Mono.justOrEmpty(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
                })
                .filter(header -> header.startsWith(BEARER_PREFIX))
                .map(header -> header.substring(BEARER_PREFIX.length()))
                .map(token -> new JwtAuthenticationToken(token, null));
    }
}
