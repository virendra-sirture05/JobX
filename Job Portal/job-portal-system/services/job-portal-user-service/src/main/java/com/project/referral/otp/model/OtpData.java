package com.project.referral.otp.model;

import com.project.referral.user.entity.UserRole;

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