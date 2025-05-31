package study.data_jpa.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 안전한 키 자동 생성
    private final long accessTokenValidity = 60 * 60 * 1000L;           // 1시간
    private final long refreshTokenValidity = 30L * 24 * 60 * 60 * 1000; // 30일

    // Access Token 생성 (userId 포함)
    public String createToken(Long userId, String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    // Refresh Token 생성 (userId 포함)
    public String createRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    // 토큰에서 username 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰에서 userId 추출
    public Long getUserId(String token) {
        return ((Number) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId")).longValue();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
