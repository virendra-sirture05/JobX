package com.project.referral.auth.service;

import com.project.referral.auth.dto.LoginRequest;
import com.project.referral.auth.dto.LoginResponse;
import com.project.referral.auth.dto.SendOtpRequest;
import com.project.referral.auth.dto.SignupResponse;
import com.project.referral.auth.dto.VerifyOtpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<LoginResponse> login(LoginRequest request);

    void sendOtp(SendOtpRequest request);

    SignupResponse verifyOtp(VerifyOtpRequest request);

    void resendOtp(String email);
    public ResponseEntity<?> refreshToken(HttpServletRequest request);

    ResponseEntity<?> logout(HttpServletRequest request);
}