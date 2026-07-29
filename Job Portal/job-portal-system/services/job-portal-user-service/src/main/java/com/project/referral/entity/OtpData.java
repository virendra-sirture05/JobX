package com.project.referral.entity;

import com.project.referral.common.domain.UserRole;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OtpData {

    private String email;

    private String password;

    private UserRole userRole;

    private String otp;

}