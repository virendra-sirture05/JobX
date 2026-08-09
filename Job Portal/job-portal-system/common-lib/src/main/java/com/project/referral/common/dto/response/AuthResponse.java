package com.project.referral.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private String title;
    private String message;
    private UserResponse user;

    public AuthResponse(String message) {
        this.message = message;
    }
}
