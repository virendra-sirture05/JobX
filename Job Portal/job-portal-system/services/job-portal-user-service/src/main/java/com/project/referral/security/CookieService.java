package com.project.referral.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    /**
     * Access Token Cookie
     */
    public ResponseCookie createAccessTokenCookie(String accessToken) {

        return ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)          // true in production (HTTPS)
                .path("/")
                .sameSite("None")
                .maxAge(accessExpiration / 1000)
                .build();
    }

    /**
     * Refresh Token Cookie
     */
    public ResponseCookie createRefreshTokenCookie(String refreshToken) {

        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)          // true in production
                .path("/")
                .sameSite("None")
                .maxAge(refreshExpiration / 1000)
                .build();
    }

    /**
     * Delete Access Token Cookie
     */
    public ResponseCookie deleteAccessTokenCookie() {

        return ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();
    }

    /**
     * Delete Refresh Token Cookie
     */
    public ResponseCookie deleteRefreshTokenCookie() {

        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();
    }

}