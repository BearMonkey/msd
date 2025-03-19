package org.monkey.msd.cloud.common.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * JwtUtil
 *
 * @author cc
 * @since 2025/3/19 9:37
 */
public class JwtUtil {
    private static final String SECRET_KEY = "your-256-bit-secret11111111111112222222222222222";
    private static final long EXPIRATION = 86400000; // 24小时


    public static String generateToken2Claims(String username, List<String> authorities) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        JwtBuilder builder = Jwts.builder()
                .header()
                .add("username", username)
                .and()
                .claim("auth", authorities)
                .signWith(secretKey)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION));
        return builder.compact();
    }

    public static String generateToken2Payload(String username, List<String> authorities) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .header()
                .add("username", username)
                .and()
                .content(JSONObject.toJSONString(authorities))
                .signWith(secretKey)
//                .expiration(new Date(System.currentTimeMillis() + EXPIRATION));
                .compact();
    }

    public static Claims parseToken2Claims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public static String parseToken2Payload(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        byte[] payload = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedContent(token)
                .getPayload();
        return new String(payload, StandardCharsets.UTF_8);

    }

    public static void main(String[] args) {
        String jwt = generateToken2Claims("monkey", List.of("admin"));
        System.out.println(jwt);

        Claims claims = parseToken2Claims(jwt);
        List<?> list = claims.get("auth", List.class);
        System.out.println(list);

        String jwt1 = generateToken2Payload("monkey", List.of("guest"));
        System.out.println(jwt1);

        String payload = parseToken2Payload(jwt1);
        System.out.println(payload);
    }
}
