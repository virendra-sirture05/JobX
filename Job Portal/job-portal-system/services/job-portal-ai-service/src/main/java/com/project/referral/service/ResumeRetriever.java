package com.project.referral.service;

import com.project.referral.dto.response.JobRequirements;
import com.project.referral.dto.response.ResumeChunk;
import java.util.List;
public interface ResumeRetriever {
    List<ResumeChunk> search(JobRequirements requirements, int topK);
}
