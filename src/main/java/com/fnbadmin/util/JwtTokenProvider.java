package com.fnbadmin.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "ti60stz9vvSa0zBMXQMi9kMxgOAf+UKDB9LbQVeKcJPzLp7DRd9Ko8Esw6l7s/5DdMhvl36R6hTlCEX0TCbJDw==";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generate(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * 토큰에서 사용자 이름(subject) 추출
     */
    public String parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validate(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            // 다양한 JWT 예외 처리 (만료, 잘못된 서명 등)
            // 실제 환경에서는 로깅 필요
        }
        return false;
    }
}