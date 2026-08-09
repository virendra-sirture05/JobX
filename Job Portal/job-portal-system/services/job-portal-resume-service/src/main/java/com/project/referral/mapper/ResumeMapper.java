package com.project.referral.mapper;


import com.project.referral.common.dto.response.*;
import com.project.referral.dto.response.ResumeResponse;
import com.project.referral.entity.*;

import java.util.Collections;
import java.util.List;

public class ResumeMapper {

    private ResumeMapper() {}

    public static ResumeResponse toResponse(Resume resume) {
        if (resume == null) return null;
        return ResumeResponse.builder()
                .id(resume.getId())
                .candidateId(resume.getCandidateId())
                .title(resume.getTitle())
                .template(resume.getTemplate())
                .visibility(resume.getVisibility())
                .isDefault(resume.getIsDefault())
                .personalInfo(resume.getPersonalInfo())
                .summary(resume.getSummary())
                .uploadedFileUrl(resume.getUploadedFileUrl())
                .uploadedFileName(resume.getUploadedFileName())
                .completionScore(resume.getCompletionScore())
                .active(resume.getActive())
                .lastViewedAt(resume.getLastViewedAt())
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .build();
    }

    public static WorkExperienceResponse toWorkExperienceResponse(
            WorkExperience exp) {
        if (exp == null) return null;
        return WorkExperienceResponse.builder()
                .id(exp.getId())
                .companyName(exp.getCompanyName())
                .companyLogoUrl(exp.getCompanyLogoUrl())
                .jobTitle(exp.getJobTitle())
                .employmentType(exp.getEmploymentType())
                .location(exp.getLocation())
                .startDate(exp.getStartDate())
                .endDate(exp.getEndDate())
                .isCurrentJob(exp.getIsCurrentJob())
                .description(exp.getDescription())
                .technologies(exp.getTechnologies())
                .displayOrder(exp.getDisplayOrder())
                .build();
    }

    public static EducationResponse toEducationResponse(Education education) {
        if (education == null) return null;
        return EducationResponse.builder()
                .id(education.getId())
                .institutionName(education.getInstitutionName())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .grade(education.getGrade())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .isCurrentlyStudying(education.getIsCurrentlyStudying())
                .description(education.getDescription())
                .displayOrder(education.getDisplayOrder())
                .build();
    }

    public static ProjectResponse toProjectResponse(Project project) {
        if (project == null) return null;
        return ProjectResponse.builder().id(project.getId()).title(project.getTitle())
                .description(project.getDescription()).technologies(project.getTechnologies())
                .projectUrl(project.getProjectUrl()).sourceCodeUrl(project.getSourceCodeUrl())
                .startDate(project.getStartDate()).endDate(project.getEndDate())
                .isOngoing(project.getIsOngoing()).displayOrder(project.getDisplayOrder()).build();
    }

    public static ResumeSkillResponse toSkillResponse(ResumeSkill skill) {
        if (skill == null) return null;
        return ResumeSkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .proficiencyLevel(skill.getProficiencyLevel())
                .yearsOfExperience(skill.getYearsOfExperience())
                .displayOrder(skill.getDisplayOrder())
                .build();
    }




}
