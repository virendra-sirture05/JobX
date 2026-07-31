package com.project.referral.mapper;


import com.project.referral.common.dto.response.*;
import com.project.referral.dto.response.PersonalInfoResponse;
import com.project.referral.dto.response.ResumeResponse;
import com.project.referral.entity.*;
import com.project.referral.entity.embeddable.PersonalInfo;


import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

public class ResumeMapper {

    private ResumeMapper() {
    }
    public static PersonalInfoResponse toPersonalInfoResponse(PersonalInfo personalInfo) {
        if (personalInfo == null) {
            return null;
        }

        return PersonalInfoResponse.builder()
                .firstName(personalInfo.getFirstName())
                .lastName(personalInfo.getLastName())
                .email(personalInfo.getEmail())
                .phone(personalInfo.getPhone())
                .headline(personalInfo.getHeadline())
                .country(personalInfo.getCountry())
                .city(personalInfo.getCity())
                .githubUrl(personalInfo.getGithubUrl())
                .linkedinUrl(personalInfo.getLinkedinUrl())
                .portfolioUrl(personalInfo.getPortfolioUrl())
                .websiteUrl(personalInfo.getWebsiteUrl())
                .build();
    }

    public static ResumeResponse toResponse(Resume resume,
                                            List<WorkExperience> workExperiences,
                                            List<Education> educations,
                                            List<ResumeSkill> skills,
                                            List<Project> projects,
                                            List<Language> languages) {
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
                .workExperiences(workExperiences == null ? Collections.emptyList()
                        : workExperiences.stream().map(ResumeMapper::toWorkExperienceResponse).toList())
                .educations(educations == null ? Collections.emptyList()
                        : educations.stream().map(ResumeMapper::toEducationResponse).toList())
                .skills(skills == null ? Collections.emptyList()
                        : skills.stream().map(ResumeMapper::toSkillResponse).toList())
                .projects(projects == null ? Collections.emptyList()
                        : projects.stream().map(ResumeMapper::toProjectResponse).toList())
                //todo
                /*.certifications(certifications == null ? Collections.emptyList()
                        : certifications.stream().map(ResumeMapper::toCertificationResponse).toList())
                .awards(awards == null ? Collections.emptyList()
                        : awards.stream().map(ResumeMapper::toAwardResponse).toList())*/

                .languages(languages == null ? Collections.emptyList()
                        : languages.stream().map(ResumeMapper::toLanguageResponse).toList())
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

    public static EducationResponse toEducationResponse(Education edu) {

        if (edu == null) return null;
        return EducationResponse.builder()
                .id(edu.getId())
                .institutionName(edu.getInstitutionName())
                .degree(edu.getDegree())
                .fieldOfStudy(edu.getFieldOfStudy())
                .grade(edu.getGrade())
                .startDate(edu.getStartDate())
                .endDate(edu.getEndDate())
                .isCurrentlyStudying(edu.getIsCurrentlyStudying())
                .description(edu.getDescription())
                .displayOrder(edu.getDisplayOrder())
                .build();
    }

    public static ProjectResponse toProjectResponse(Project project) {
        if (project == null) return null;
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .projectUrl(project.getProjectUrl())
                .sourceCodeUrl(project.getSourceCodeUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .isOngoing(project.getIsOngoing())
                .displayOrder(project.getDisplayOrder())
                .build();
    }


    public static LanguageResponse toLanguageResponse(Language lang) {
        if (lang == null) return null;
        return LanguageResponse.builder()
                .id(lang.getId())
                .languageName(lang.getLanguageName())
                .proficiency(lang.getProficiency())
                .displayOrder(lang.getDisplayOrder())
                .build();
    }
}
