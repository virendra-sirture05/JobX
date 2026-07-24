package com.project.referral.service;

import com.project.referral.dto.request.SavedJobRequest;
import com.project.referral.common.dto.response.SavedJobResponse;

import java.util.List;

public interface SavedJobService {
    SavedJobResponse saveJob(Long candidate, SavedJobRequest req) throws Exception;
    void unsaveJob(Long candidateId, Long savedJobId) throws Exception;
    List<SavedJobResponse> getSavedJob(Long candidateId);
    boolean isSaved(Long candidateId, Long jobId);
}
