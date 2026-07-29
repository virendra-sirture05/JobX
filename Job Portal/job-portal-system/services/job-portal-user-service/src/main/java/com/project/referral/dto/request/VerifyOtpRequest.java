package com.project.referral.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerifyOtpRequest {

    private String email;

    private String otp;

}