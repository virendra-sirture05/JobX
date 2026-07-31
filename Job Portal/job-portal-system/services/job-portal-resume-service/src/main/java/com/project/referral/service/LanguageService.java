package com.project.referral.service;

import com.project.referral.common.dto.response.LanguageResponse;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddLanguageRequest;

import java.util.List;

public interface LanguageService {
    LanguageResponse addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req)
            throws ResourceNotFoundException;

    List<LanguageResponse> getLanguages(Long resumeId) throws ResourceNotFoundException;

    LanguageResponse updateLanguage(Long languageId, Long resumeId, Long candidateId,
                                    AddLanguageRequest req) throws ResourceNotFoundException;

    void deleteLanguage(Long languageId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException;
}

