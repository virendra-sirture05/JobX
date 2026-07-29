package com.project.referral.service;

import com.project.referral.dto.response.ResumeChunk;
import java.util.List;
public interface ChunkingService {
    /**
     * Break resume text into semantic chunks according to configured chunk size and overlap.
     * @param text full resume text
     * @param resumeId optional resume id for metadata
     * @return ordered list of ResumeChunk
     */
    List<ResumeChunk> chunk(String text, String resumeId);
}
