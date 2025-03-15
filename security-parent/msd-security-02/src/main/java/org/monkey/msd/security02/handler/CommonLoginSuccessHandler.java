package org.monkey.msd.security02.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * CommonLoginSuccessHandler
 *
 * @author cc
 * @since 2025/2/7 15:28
 */
@Component
@Slf4j
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info(authentication.getAuthorities().toString());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<String> auth = authorities.stream().map(GrantedAuthority::getAuthority).filter(a -> a.equals("read")).findFirst();
        log.info("auth:{}",authorities);
        if(auth.isPresent()){
            log.info("您有足够的权限访问此资源");
            response.sendRedirect("/user/hello");
        }else {
            log.info("您没有足够的权限访问此资源");
            response.sendRedirect("/user/error");
        }

    }
}
