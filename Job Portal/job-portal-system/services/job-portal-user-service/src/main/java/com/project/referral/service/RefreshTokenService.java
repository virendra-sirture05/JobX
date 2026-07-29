package com.project.referral.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public void save(String email, String refreshToken) {

        redisTemplate.opsForValue().set(
                "refresh:" + email,
                refreshToken,
                Duration.ofMillis(refreshExpiration)
        );

    }

    public String get(String email) {

        return (String) redisTemplate.opsForValue()
                .get("refresh:" + email);

    }

    public void delete(String email) {

        redisTemplate.delete("refresh:" + email);

    }

}
