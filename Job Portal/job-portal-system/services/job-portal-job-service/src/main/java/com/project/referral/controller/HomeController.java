package com.project.referral.controller;

import com.project.referral.common.domain.UserRole;
import com.project.referral.common.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ApiResponse home() {
        return new ApiResponse(
                "Service for managing job posting, search and filtering---"+ UserRole.ROLE_JOBSEEKER,
                true
        );
    }
}