package com.example.umcspringbootstudy.global.auth.jwt;

import com.example.umcspringbootstudy.global.auth.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessExpiration;

    // application.yml의 jwt 값을 생성자에서 주입받음
    public JwtUtil(
            @Value("${jwt.token.secretKey}") String secret,
            @Value("${jwt.token.expiration.access}") Long accessExpiration
    ) {
        // 문자열 시크릿 키 → HMAC-SHA 알고리즘용 SecretKey 객체로 변환
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        // 밀리초 숫자 → Duration 타입으로 변환 (14400000ms = 4시간)
        this.accessExpiration = Duration.ofMillis(accessExpiration);
    }

    /**
     * Access Token 발급
     * 로그인 성공 시 UserService에서 호출
     */
    public String createAccessToken(CustomUserDetails user) {
        return createToken(user, accessExpiration);
    }

    public String getEmail(String token) {
        return getClaims(token).getPayload().getSubject();
    }

    // 토큰 생성 내부 로직
    private String createToken(CustomUserDetails user, Duration expiration) {
        Instant now = Instant.now();

        // 권한 목록 → 콤마 구분 문자열 변환 (예: "ROLE_USER")
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(user.getUsername())                 // 이메일을 subject로 저장
                .claim("role", authorities)                  // 권한 정보 저장
                .issuedAt(Date.from(now))                    // 발급 시간
                .expiration(Date.from(now.plus(expiration))) // 만료 시간
                .signWith(secretKey)                         // 서명 (위변조 방지)
                .compact();                                  // 최종 토큰 문자열 반환
    }

    // 토큰 파싱 + 서명 검증
    // 서명이 잘못됐거나 만료되면 JwtException 발생
    // clockSkewSeconds(60): 서버 간 시간 오차 1분까지 허용
    private Jws<Claims> getClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(token);
    }
}
