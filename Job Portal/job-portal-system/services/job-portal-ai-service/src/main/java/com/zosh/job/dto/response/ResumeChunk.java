package com.project.referral.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeChunk {
    private String resumeId;
    private String candidateId;
    private int chunkNumber;
    private int startIndex;
    private int endIndex;
    private Integer pageNumber;
    private String section;
    private String text;
}
