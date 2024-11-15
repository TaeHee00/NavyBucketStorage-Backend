package com.kancth.navybucketstorage.domain.security.service;

import com.kancth.navybucketstorage.domain.security.entity.*;
import com.kancth.navybucketstorage.domain.security.exception.*;
import com.kancth.navybucketstorage.domain.security.repository.*;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final long ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 30; // 30분
    private final long REFRESH_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60 * 24 * 14; // 2주
    private final String ACCESS_PREFIX_STRING = "Bearer ";
    private final String ACCESS_HEADER_STRING = "Authorization";
    private final String REFRESH_HEADER_STRING = "RefreshToken";

    private final byte[] keyBytes;
    private final Key signingKey;

    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Autowired
    public JwtService(UserService userService,
                      RefreshTokenRepository refreshTokenRepository,
                      JwtProperties jwtProperties) {
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProperties = jwtProperties;
        this.keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.signingKey = Keys.hmacShaKeyFor(this.keyBytes);
    }

    public JwtToken createJwtToken(User user) {
        AccessToken accessToken = this.genAccessToken(user.getId());
        RefreshToken refreshToken = this.createRefreshToken(user.getId(), accessToken);

        return JwtToken.of(accessToken.getAccessToken(), refreshToken);
    }

    public Jws<Claims> parseClaims(String token) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser()
                    .verifyWith((SecretKey) signingKey)
                    .build()
                    .parseSignedClaims(token);
        } catch(ExpiredJwtException ex) {
            throw new ExpiredTokenException();
            // TODO: 만료 시 AccessToken 재발급 메서드 추가
            //  return null; // 만료되었음
        } catch(JwtException ex) {
            throw new InvalidTokenException();
        }
        return claimsJws;
    }

    private AccessToken createAccessToken(Long userId) {
        return this.genAccessToken(userId);
    }

    // AccessToken 재발급
    private AccessToken createAccessToken(RefreshToken refreshToken) {
        Jws<Claims> claimsJws = parseClaims(refreshToken.getRefreshToken());
        Long userId = claimsJws.getPayload().get("userId", Long.class);

        return this.genAccessToken(userId);
    }

    private RefreshToken createRefreshToken(Long userId, AccessToken accessToken) {
        Date expiration = new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_SECONDS);

        String refreshToken = Jwts.builder()
                                .subject(Long.toString(userId))
                                .claim("userId", userId)
                                .claim("accessToken", accessToken)
                                .expiration(expiration)
                                .signWith(signingKey)
                                .compact();

        // 이전에 생성된 RefreshToken 삭제
        refreshTokenRepository.deleteById(userId);

        return refreshTokenRepository.save(RefreshToken.of(refreshToken, accessToken.getAccessToken()));
    }

    private AccessToken genAccessToken(Long userId) {
        Date expiration = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS);
        String accessToken = ACCESS_PREFIX_STRING + Jwts.builder()
                .subject(Long.toString(userId))
                .claim("userId", userId)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();

        return AccessToken.of(accessToken);
    }

    private RefreshToken getRefreshTokenByMemberId(String accessToken) {
        return refreshTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    public User getUser(String token) {
        Jws<Claims> claims = this.parseClaims(token);
        return userService.getUser(claims.getPayload().get("userId", Long.class));
    }
}
