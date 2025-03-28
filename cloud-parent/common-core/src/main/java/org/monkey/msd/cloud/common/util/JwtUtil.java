package org.monkey.msd.cloud.common.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * JwtUtil
 *
 * @author cc
 * @since 2025/3/19 9:37
 */
public class JwtUtil {
    private static final String SECRET_KEY = "your-256-bit-secret11111111111112222222222222222";
    private static final long EXPIRATION = 86400000; // 24小时


    public static String generateToken2Claims(
            String secret,
            Map<String, Object> header,
            Map<String, Object> claims,
            long expiration) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        JwtBuilder builder = Jwts.builder()
                .header()
                .add(header)
                .and()
                .claims(claims)
                .signWith(secretKey)
                .expiration(new Date(System.currentTimeMillis() + expiration));
        return builder.compact();
    }

    public static String generateToken2Payload(
            String secret,
            Map<String, Object> header,
            String content) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .header()
                .add(header)
                .and()
                .content(content)
                .signWith(secretKey)
                .compact();
    }

    public static Claims parseToken2Claims(String token, String secret) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public static String parseToken2Payload(String token, String secret) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        byte[] payload = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedContent(token)
                .getPayload();
        return new String(payload, StandardCharsets.UTF_8);

    }

    public static boolean checkJwt(String token, String secret) {
        try {
            JwtUtil.parseToken2Claims(token, secret);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("无效的JWT令牌");
        }
    }

    public static void main(String[] args) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("username", "monkey");
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("auth", Set.of("admin"));
        String jwt = generateToken2Claims(SECRET_KEY, headerMap, claimsMap, EXPIRATION);
        System.out.println(jwt);

        Claims claims = parseToken2Claims(jwt, SECRET_KEY);
        List<?> list = claims.get("auth", List.class);
        System.out.println(list);

        String jwt1 = generateToken2Payload(SECRET_KEY, headerMap, JSONObject.toJSONString(List.of("guest")));
        System.out.println(jwt1);

        String payload = parseToken2Payload(jwt1, SECRET_KEY);
        System.out.println(payload);
    }
}
