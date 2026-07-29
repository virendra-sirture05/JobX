package com.project.referral.controller;

import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.dto.request.CompanyDescriptionRequest;
import com.project.referral.dto.request.CompanyTaglineRequest;
import com.project.referral.dto.response.AiTextResponse;
import com.project.referral.dto.response.CompanyTaglineResponse;
import com.project.referral.service.CompanyAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/company")
@RequiredArgsConstructor
public class AiCompanyController {

    private final CompanyAiService companyAiService;

    /**
     * Phase 1: Generate company description for employer profile.
     * Used in CompanyProfile form.
     * POST /api/ai/company/describe
     */
    @PostMapping("/describe")
    public ResponseEntity<ApiResponse<AiTextResponse>> generateCompanyDescription(
            @Valid @RequestBody CompanyDescriptionRequest request) {
        AiTextResponse response = companyAiService.generateCompanyDescription(request);
        return ResponseEntity.ok(ApiResponse.success("Company description generated", response));
    }

    /**
     * Phase 1: Generate 3 tagline options for the company.
     * Used in CompanyProfile form.
     * POST /api/ai/company/taglines
     */
    @PostMapping("/taglines")
    public ResponseEntity<ApiResponse<CompanyTaglineResponse>> generateTaglines(
            @Valid @RequestBody CompanyTaglineRequest request) {
        CompanyTaglineResponse response = companyAiService.generateCompanyTaglines(request);
        return ResponseEntity.ok(ApiResponse.success("Taglines generated", response));
    }
}
