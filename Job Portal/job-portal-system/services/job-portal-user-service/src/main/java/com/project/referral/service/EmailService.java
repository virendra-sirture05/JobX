package com.project.referral.service;


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