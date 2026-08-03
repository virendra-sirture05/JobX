package com.project.referral.common.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.AuthProvider;
import com.project.referral.common.domain.UserRole;
import com.project.referral.common.domain.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
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
    public UserResponse() {
    }

    public UserResponse(Long id, String fullName, String email, String phone, String profileImage, UserRole role, AuthProvider authProvider, UserStatus status, Boolean verified, LocalDateTime lastLogin, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.profileImage = profileImage;
        this.role = role;
        this.authProvider = authProvider;
        this.status = status;
        this.verified = verified;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
    }
}
