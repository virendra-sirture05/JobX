package com.project.referral.auth.dto;

import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerifyOtpRequest {

    private String email;

    private String otp;

}