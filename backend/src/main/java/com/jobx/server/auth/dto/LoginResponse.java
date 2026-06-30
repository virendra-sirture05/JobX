package com.jobx.server.auth.dto;

import com.jobx.server.user.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long userId;

    private String email;

    private UserRole role;

    private String message;

}