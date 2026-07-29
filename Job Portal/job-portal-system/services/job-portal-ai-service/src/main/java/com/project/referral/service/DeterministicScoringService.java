package com.project.referral.service;

import com.project.referral.dto.response.CandidateEvaluation;
import com.project.referral.dto.response.JobRequirements;
import com.project.referral.dto.response.ResumeChunk;
import com.project.referral.dto.response.ResumeParseResponse;
import java.util.List;
public interface DeterministicScoringService {
    CandidateEvaluation evaluate(JobRequirements requirements,
                                 ResumeParseResponse parsedResume,
                                 List<ResumeChunk> topChunks);
}
