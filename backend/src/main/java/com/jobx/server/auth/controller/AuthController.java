package com.jobx.server.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobx.server.auth.dto.LoginRequest;
import com.jobx.server.auth.dto.LoginResponse;
import com.jobx.server.auth.dto.SendOtpRequest;
import com.jobx.server.auth.dto.SignupResponse;
import com.jobx.server.auth.dto.VerifyOtpRequest;
import com.jobx.server.auth.service.AuthService;
import com.jobx.server.web.ApiResponse;

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
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request){

        log.info("POST /api/auth/login");

        return ResponseEntity.ok(
                ApiResponse.success(
                        authService.login(request),
                        "Login successful"
                )
        );
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
}