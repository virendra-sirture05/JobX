package com.project.referral.service.impl;
import com.project.referral.service.RefreshTokenService;
import com.project.referral.repository.UserRepository;
import com.project.referral.security.CookieService;
import com.project.referral.security.JwtService;
import com.project.referral.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.referral.dto.request.LoginRequest;
import com.project.referral.dto.request.LoginResponse;
import com.project.referral.dto.request.SendOtpRequest;
import com.project.referral.dto.request.SignupResponse;
import com.project.referral.dto.request.VerifyOtpRequest;
import com.project.referral.service.CacheService;
import com.project.referral.service.EmailService;
import com.project.referral.util.OtpGenerator;
import com.project.referral.entity.OtpData;
import com.project.referral.entity.User;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl
        implements AuthService {

    private final CacheService cacheService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final OtpGenerator otpGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final RefreshTokenService refreshTokenService;

    @Override

    public ResponseEntity<LoginResponse> login(LoginRequest request) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(),
                                    request.getPassword()
                            )
                    );
            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername());
            String accessToken =
                    jwtService.generateAccessToken(user.getEmail());

            String refreshToken =
                    jwtService.generateRefreshToken(user.getEmail());

            refreshTokenService.save(
                    user.getEmail(),
                    refreshToken
            );

            ResponseCookie accessCookie =
                    cookieService.createAccessTokenCookie(accessToken);

            ResponseCookie refreshCookie =
                    cookieService.createRefreshTokenCookie(refreshToken);

            LoginResponse response =
                    new LoginResponse(
                            user.getId(),
                            user.getEmail(),
                            user.getRole(),
                            "Login successful"
                    );

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body(response);

        } catch (AuthenticationException ex) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse());
        }
    }

    @Override
    public void sendOtp(SendOtpRequest request) {

        String otp = otpGenerator.generateOtp();

        OtpData otpData =
                new OtpData();

        otpData.setEmail(
                request.getEmail()
        );
        String encodedPassword =
                passwordEncoder.encode(
                        request.getPassword()
                );
        otpData.setPassword(encodedPassword);
        	log.info("role is {}",request);
        otpData.setUserRole(
                request.getUserRole()
        );

        otpData.setOtp(
                otp
        );

   
        log.info(
                "OTP generated for {}",
                request.getEmail()
        );
        cacheService.saveOtp(
                request.getEmail(),
                otpData
        );

        emailService.sendVerificationOtp(
                request.getEmail(),
                otp
        );

     

    }

    @Override
    public SignupResponse verifyOtp(
            VerifyOtpRequest request
    ) {

        OtpData otpData =
                cacheService.getOtp(
                        request.getEmail()
                );

        if (otpData == null) {

            throw new RuntimeException(
                    "OTP Expired"
            );

        }

        if (!otpData.getOtp()
                .equals(request.getOtp())) {

            throw new RuntimeException(
                    "Invalid OTP"
            );

        }
        else{
            cacheService.deleteOtp(
                    request.getEmail()
            );
        }

        User user = new User(request.getEmail(),otpData.getPassword(),otpData.getUserRole());
        userRepository.save(user);
        SignupResponse response =
                new SignupResponse();

        response.setMessage(
                "Signup Successful"
        );

        return response;

    }

    @Override
    public void resendOtp(
            String email
    ) {

        OtpData otpData =
                cacheService.getOtp(
                        email
                );

        if (otpData == null) {

            throw new RuntimeException(
                    "Signup session expired"
            );

        }

        String otp = otpGenerator.generateOtp();

        otpData.setOtp(
                otp
        );


        cacheService.saveOtp(
                email,
                otpData
        );

        emailService.sendVerificationOtp(
                email,
                otp
        );
    }
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request) {

        String refreshToken = getRefreshToken(request);

        if (refreshToken == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token missing");

        }

        String email;

        try {

            email = jwtService.extractEmail(refreshToken);

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid refresh token");

        }

        User user = userRepository.findByEmail(email);

        if (!jwtService.isTokenValid(refreshToken, user.getEmail())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token expired");

        }

        String storedToken =
                refreshTokenService.get(email);

        if (storedToken == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Session expired");

        }

        if (!storedToken.equals(refreshToken)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token mismatch");

        }

        String newAccessToken =
                jwtService.generateAccessToken(email);

        ResponseCookie cookie =
                cookieService.createAccessTokenCookie(
                        newAccessToken
                );

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.SET_COOKIE,
                        cookie.toString()
                )

                .body(
                        Map.of(
                                "success", true,
                                "message", "Token refreshed"
                        )
                );

    }
    private String getRefreshToken(HttpServletRequest request) {

        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }

        }
        return null;

    }

    public ResponseEntity<?> logout(
            HttpServletRequest request) {

        String refreshToken = getRefreshToken(request);

        if (refreshToken != null) {

            try {

                String email =
                        jwtService.extractEmail(refreshToken);

                refreshTokenService.delete(email);

            } catch (Exception ignored) {

            }

        }

        ResponseCookie accessCookie =
                cookieService.deleteAccessTokenCookie();

        ResponseCookie refreshCookie =
                cookieService.deleteRefreshTokenCookie();

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.SET_COOKIE,
                        accessCookie.toString()
                )

                .header(
                        HttpHeaders.SET_COOKIE,
                        refreshCookie.toString()
                )

                .body(
                        Map.of(
                                "success", true,
                                "message", "Logout successful"
                        )
                );

    }
}