package com.project.referral.email;


public interface EmailService {

    void sendVerificationOtp(
            String email,
            String otp
    );

    void sendForgotPasswordOtp(
            String email,
            String otp
    );

}