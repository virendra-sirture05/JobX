package com.project.referral.controller;

import com.project.referral.common.dto.request.LoginRequest;
import com.project.referral.common.dto.response.AuthResponse;
import com.project.referral.common.exception.UserException;
import com.project.referral.dto.request.SignupRequest;
import com.project.referral.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody @Valid SignupRequest req) throws UserException {
        return ResponseEntity.ok(authService.signup(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest req) throws UserException {
        return ResponseEntity.ok(authService.login(req));
    }
}
