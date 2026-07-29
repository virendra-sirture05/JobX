package com.project.referral.otp.util;


import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public final class OtpGenerator {

    private static final SecureRandom
            RANDOM =
            new SecureRandom();

    private OtpGenerator() {

    }

    public String generateOtp() {

        int otp =100000 +RANDOM.nextInt(900000);

        return String.valueOf(otp);

    }

}