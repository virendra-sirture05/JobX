package com.project.referral.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    private String phone;

    private String profileImage;
}
