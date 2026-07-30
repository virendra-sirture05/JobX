package com.project.referral.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.ResumeTemplate;
import com.project.referral.common.domain.ResumeVisibility;
import com.project.referral.common.dto.response.*;
import com.project.referral.entity.embeddable.PersonalInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeResponse {

    private Long id;
    private Long candidateId;
    private String title;
    private ResumeTemplate template;
    private ResumeVisibility visibility;
    private Boolean isDefault;
    private PersonalInfo personalInfo;
    private String summary;
    private String uploadedFileUrl;
    private String uploadedFileName;
    private Integer completionScore;
    private Boolean active;
    private LocalDateTime lastViewedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<WorkExperienceResponse> workExperiences;
   private List<EducationResponse> educations;
   private List<ResumeSkillResponse> skills;
   private List<ProjectResponse> projects;
   // private List<CertificationResponse> certifications;
   // private List<AwardResponse> awards;
   private List<LanguageResponse> languages;
}
