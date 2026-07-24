package com.project.referral.mapper;

import com.project.referral.model.SavedJob;
import com.project.referral.common.dto.response.SavedJobResponse;

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
