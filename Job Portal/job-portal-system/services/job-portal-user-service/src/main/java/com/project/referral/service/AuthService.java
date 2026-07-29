package com.project.referral.service;

import com.project.referral.dto.request.LoginRequest;
import com.project.referral.dto.request.LoginResponse;
import com.project.referral.dto.request.SendOtpRequest;
import com.project.referral.dto.request.SignupResponse;
import com.project.referral.dto.request.VerifyOtpRequest;
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