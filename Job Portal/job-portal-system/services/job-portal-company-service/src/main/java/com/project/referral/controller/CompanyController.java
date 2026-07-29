package com.project.referral.controller;

import com.project.referral.common.domain.CompanyStatus;
import com.project.referral.common.domain.CompanyType;
import com.project.referral.common.domain.IndustryType;
import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.common.dto.response.CompanyResponse;
import com.project.referral.common.dto.response.CompanySummaryResponse;
import com.project.referral.common.exception.CompanyException;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.CompanyRequest;
import com.project.referral.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest req) throws CompanyException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(ownerId, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<CompanySummaryResponse> getCompanySummaryById(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.getCompanySummaryById(id));
    }


    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> getMyCompany(
            @RequestHeader("X-User-Id") Long ownerId) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus status) {
        return ResponseEntity.ok(companyService.getAllCompanies(companyType, industryType, status));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest req)
            throws ResourceNotFoundException, CompanyException {
        return ResponseEntity.ok(companyService.updateCompany(id, ownerId, req));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.deactivateCompany(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId)
            throws ResourceNotFoundException, CompanyException {
        companyService.deleteCompany(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Company deleted successfully", true));
    }
}
