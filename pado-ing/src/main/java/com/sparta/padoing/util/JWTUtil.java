package com.sparta.padoing.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret){
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        // Jwts.parser()를 사용하여 토큰을 파싱하고, 비밀 키로 검증한 뒤 claims에서 "role" 값을 가져옴
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        // Jwts.parser()를 사용하여 토큰을 파싱하고, 비밀 키로 검증한 뒤 claims에서 만료 시간을 가져와 현재 시간과 비교
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // JWT 토큰을 생성하는 메서드
    public String createJwt(String username, String role, Long expiredMs) {
        // Jwts.builder()를 사용하여 JWT 토큰을 생성
        // "username"과 "role" 클레임을 설정하고, 현재 시간과 만료 시간을 설정
        // 마지막으로 비밀 키로 서명하여 토큰을 생성
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}