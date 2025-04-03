package org.monkey.msd.cloud.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;

/**
 * GiteeClient
 *
 * @author cc
 * @since 2025/4/2 18:05
 */
//@Component
public class GithubClient {

    @Bean
    public ClientRegistration getClientRegistration(){
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId("Iv23likzANIcZ9Ic95X8")
                .clientSecret("7af60610602c74368020230ea6eb0f2469601693")
                .build();
    }
}
