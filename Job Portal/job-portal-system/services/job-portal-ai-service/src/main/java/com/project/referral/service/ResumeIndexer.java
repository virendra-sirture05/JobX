package com.project.referral.service;

import com.project.referral.dto.response.ResumeChunk;
import java.util.List;
public interface ResumeIndexer {
    void indexResume(String resumeId, String candidateId, List<ResumeChunk> chunks);
    void updateResume(String resumeId, List<ResumeChunk> chunks);
    void deleteResume(String resumeId);
}
