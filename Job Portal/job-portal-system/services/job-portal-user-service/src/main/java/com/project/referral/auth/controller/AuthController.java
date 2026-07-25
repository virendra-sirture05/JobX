package com.project.referral.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.referral.auth.dto.LoginRequest;
import com.project.referral.auth.dto.LoginResponse;
import com.project.referral.auth.dto.SendOtpRequest;
import com.project.referral.auth.dto.SignupResponse;
import com.project.referral.auth.dto.VerifyOtpRequest;
import com.project.referral.auth.service.AuthService;
import com.project.referral.web.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(    origins = "http://localhost:5173",
allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request){

        log.info("POST /api/auth/login");

        return  authService.login(request);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp( @Valid @RequestBody SendOtpRequest request) {
    		System.out.println(request);
        authService.sendOtp(request);
        return ResponseEntity.ok(new ApiResponse(true,"OTP sent successfully.")
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(
                authService.verifyOtp(request)
        );
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request) {

        return authService.refreshToken(request);

    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request) {

        return authService.logout(request);

    }
}