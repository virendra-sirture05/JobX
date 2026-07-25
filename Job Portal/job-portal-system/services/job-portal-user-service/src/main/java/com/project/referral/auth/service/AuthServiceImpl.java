package com.project.referral.auth.service;
import com.project.referral.exception.BadRequestException;
import com.project.referral.exception.ResourceNotFoundException;
import com.project.referral.security.CookieService;
import com.project.referral.security.JwtService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.referral.auth.dto.LoginRequest;
import com.project.referral.auth.dto.LoginResponse;
import com.project.referral.auth.dto.SendOtpRequest;
import com.project.referral.auth.dto.SignupResponse;
import com.project.referral.auth.dto.VerifyOtpRequest;
import com.project.referral.cache.CacheService;
import com.project.referral.email.EmailService;
import com.project.referral.otp.util.OtpGenerator;
import com.project.referral.otp.model.OtpData;
import com.project.referral.user.entity.User;
import com.project.referral.user.repository.UserRepository;

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

            User user = (User) authentication.getPrincipal();

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

        cacheService.deleteOtp(
                request.getEmail()
        );

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

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(email)
                );

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