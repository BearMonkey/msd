package org.monkey.msd.cloud.gateway.config;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

/**
 * GiteeClient
 *
 * @author cc
 * @since 2025/4/1 17:22
 */
@Component
public class GiteeClient {
    public ClientRegistration clientRegistration(){
        return ClientRegistration.withRegistrationId("gitee")  //起个名字,代表client，如clientId和clientSecret
                .clientId("ad010f06b3f440b33dfa6f499f27477d1747e532fd3fd0a2f28030f3f31b6ab6")  //此处要换成你在gitee上创建应用得到的
                .clientSecret("5751182aba6c6a57c0b7267ae1259aba4357395b2a0dbca42368ffdef1f463b1") //此处要换成你在gitee上创建应用得到的
                .scope(new String[]{"user_info"})    //读取用户权限，参见你gitee上创建应用时的授权勾选
                .authorizationUri("https://gitee.com/oauth/authorize")   //这要看gitee的api，是user认证以及client认证获取授权码的地址
                .tokenUri("https://gitee.com/oauth/token") //这要看gitee的api，是client得到授权码后去换token的gitee地址
                .userInfoUri("https://gitee.com/api/v5/user") //资源服务器api地址-也是client用access-token去获取用户user详情的“用户详情资源服务器地址”-这里也是gitee】】
                .userNameAttributeName("id")
                .clientName("gitee")  //为我们的应用client起了个名字
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)  //注是授权码模式
                .redirectUri("http://856gf5005iu6.vicp.fun/login/oauth2/code/gitee")
//                .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")  //本应用配置的gitee发回授权码的地址
                .build();
    }
}
