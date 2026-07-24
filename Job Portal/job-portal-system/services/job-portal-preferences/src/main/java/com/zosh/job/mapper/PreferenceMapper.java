package com.zosh.job.mapper;

import com.zosh.job.common.dto.response.SavedJobResponse;
import com.zosh.job.model.SavedJob;

public class PreferenceMapper {
    public static SavedJobResponse toSavedJobResponse(SavedJob savedjob){
        return SavedJobResponse.builder()
                .id(savedjob.getId())
                .candidateId(savedjob.getCandidateId())
                .jobId(savedjob.getJobId())
                .savedAt(savedjob.getSavedAt())
                .build();
    }
}
