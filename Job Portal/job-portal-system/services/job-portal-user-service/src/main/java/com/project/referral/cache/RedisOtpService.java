package com.project.referral.cache;


import com.project.referral.otp.model.OtpData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisOtpService implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration OTP_EXPIRY =
            Duration.ofMinutes(10);

    private String getKey(String email) {
        return "otp:" + email;
    }

    @Override
    public void saveOtp(
            String email,
            OtpData otpData
    ) {

        redisTemplate
                .opsForValue()
                .set(
                        getKey(email),
                        otpData,
                        OTP_EXPIRY
                );

    }

    @Override
    public OtpData getOtp(
            String email
    ) {

        return (OtpData)
                redisTemplate
                        .opsForValue()
                        .get(getKey(email));

    }

    @Override
    public void deleteOtp(
            String email
    ) {

        redisTemplate.delete(
                getKey(email)
        );

    }

}