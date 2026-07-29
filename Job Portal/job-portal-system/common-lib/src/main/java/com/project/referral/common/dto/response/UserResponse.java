package com.project.referral.common.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.AuthProvider;
import com.project.referral.common.domain.UserRole;
import com.project.referral.common.domain.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String profileImage;
    private UserRole role;
    private AuthProvider authProvider;
    private UserStatus status;
    private Boolean verified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
