package com.jobx.server.otp.model;

import com.jobx.server.user.entity.UserRole;

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