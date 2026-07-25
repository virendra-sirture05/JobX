package com.project.referral.auth.service;
import com.project.referral.exception.BadRequestException;
import com.project.referral.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    @Override
    public LoginResponse login(
            LoginRequest request
    ) {

        log.info(
                "Login attempt for email={}",
                request.getEmail()
        );
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn(
                            "Login failed. User not found for email={}",
                            request.getEmail()
                    );
                    return new ResourceNotFoundException(
                            "Invalid email or password."
                    );
                });
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getHashedPassword()
        )) {

            log.warn(
                    "Login failed. Invalid password for {}",
                    request.getEmail()
            );

            throw new BadRequestException(
                    "Invalid email or password."
            );
        }
        log.info(
                "Login successful for {}",
                request.getEmail()
        );
        return LoginResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();
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
}