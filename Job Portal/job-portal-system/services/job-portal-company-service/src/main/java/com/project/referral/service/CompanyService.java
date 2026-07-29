package com.project.referral.service;


import com.project.referral.common.domain.CompanyStatus;
import com.project.referral.common.domain.CompanyType;
import com.project.referral.common.domain.IndustryType;
import com.project.referral.common.dto.response.CompanyResponse;
import com.project.referral.common.dto.response.CompanySummaryResponse;
import com.project.referral.common.exception.CompanyException;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.CompanyRequest;
import com.project.referral.entity.Company;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws CompanyException;

    CompanyResponse getCompanyById(Long id) throws ResourceNotFoundException;

    CompanySummaryResponse getCompanySummaryById(Long id) throws ResourceNotFoundException;

    CompanyResponse getMyCompany(Long ownerId) throws ResourceNotFoundException;

    List<CompanyResponse> getAllCompanies(
            CompanyType companyType,
            IndustryType industryType,
            CompanyStatus status);

    CompanyResponse updateCompany(Long companyId,
                                  Long ownerId, CompanyRequest req)
            throws ResourceNotFoundException, CompanyException;

    CompanyResponse verifyCompany(Long companyId) throws ResourceNotFoundException;

    CompanyResponse deactivateCompany(Long companyId) throws ResourceNotFoundException;

    void deleteCompany(Long companyId, Long ownerId) throws ResourceNotFoundException, CompanyException;

    /** Used internally by other services (e.g. location service). */
    Company getCompanyEntityById(Long id) throws ResourceNotFoundException;
}
