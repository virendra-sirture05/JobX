package com.jobx.server.auth.service;

import com.jobx.server.auth.dto.LoginRequest;
import com.jobx.server.auth.dto.LoginResponse;
import com.jobx.server.auth.dto.SendOtpRequest;
import com.jobx.server.auth.dto.SignupResponse;
import com.jobx.server.auth.dto.VerifyOtpRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void sendOtp(SendOtpRequest request);

    SignupResponse verifyOtp(VerifyOtpRequest request);

    void resendOtp(String email);
}