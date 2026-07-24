package com.zosh.job.service;

import com.zosh.job.common.dto.response.SavedJobResponse;
import com.zosh.job.dto.request.SavedJobRequest;

import java.util.List;

public interface SavedJobService {
    SavedJobResponse saveJob(Long candidate, SavedJobRequest req) throws Exception;
    void unsaveJob(Long candidateId, Long savedJobId) throws Exception;
    List<SavedJobResponse> getSavedJob(Long candidateId);
    boolean isSaved(Long candidateId, Long jobId);
}
