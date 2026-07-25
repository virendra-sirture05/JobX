package com.project.referral.auth.service;

import com.project.referral.auth.dto.LoginRequest;
import com.project.referral.auth.dto.LoginResponse;
import com.project.referral.auth.dto.SendOtpRequest;
import com.project.referral.auth.dto.SignupResponse;
import com.project.referral.auth.dto.VerifyOtpRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void sendOtp(SendOtpRequest request);

    SignupResponse verifyOtp(VerifyOtpRequest request);

    void resendOtp(String email);
}