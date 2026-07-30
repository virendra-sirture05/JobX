package com.project.referral.service.impl;

import com.project.referral.common.dto.response.EducationResponse;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddEducationRequest;
import com.project.referral.entity.Education;
import com.project.referral.entity.Resume;
import com.project.referral.mapper.ResumeMapper;
import com.project.referral.repository.EducationRepository;
import com.project.referral.service.EducationService;
import com.project.referral.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl  implements EducationService {
    private final EducationRepository educationRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req)
            throws ResourceNotFoundException {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);

        Education edu = Education.builder()
                .resume(resume)
                .institutionName(req.getInstitutionName())
                .degree(req.getDegree())
                .fieldOfStudy(req.getFieldOfStudy())
                .grade(req.getGrade())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isCurrentlyStudying(Boolean.TRUE.equals(req.getIsCurrentlyStudying()))
                .description(req.getDescription())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();

        return ResumeMapper.toEducationResponse(educationRepository.save(edu));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponse> getEducations(Long resumeId) throws ResourceNotFoundException {
        resumeService.getResumeEntity(resumeId);
        return educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toEducationResponse).toList();
    }

    @Override
    @Transactional
    public EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId,
                                             AddEducationRequest req) throws ResourceNotFoundException {
        Education edu = getEducationEntity(educationId, resumeId);
        assertOwner(edu.getResume(), candidateId, resumeId);

        edu.setInstitutionName(req.getInstitutionName());
        edu.setDegree(req.getDegree());
        edu.setFieldOfStudy(req.getFieldOfStudy());
        edu.setGrade(req.getGrade());
        edu.setStartDate(req.getStartDate());
        edu.setEndDate(req.getEndDate());
        edu.setIsCurrentlyStudying(Boolean.TRUE.equals(req.getIsCurrentlyStudying()));
        edu.setDescription(req.getDescription());
        if (req.getDisplayOrder() != null) edu.setDisplayOrder(req.getDisplayOrder());

        return ResumeMapper.toEducationResponse(educationRepository.save(edu));
    }

    @Override
    @Transactional
    public void deleteEducation(Long educationId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException {
        Education edu = getEducationEntity(educationId, resumeId);
        assertOwner(edu.getResume(), candidateId, resumeId);
        educationRepository.delete(edu);
    }

    private Education getEducationEntity(Long educationId, Long resumeId)
            throws ResourceNotFoundException {
        Education edu = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education not found with id: " + educationId));
        if (!edu.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException("Education not found with id: " + educationId);
        }
        return edu;
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws ResourceNotFoundException {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }
    }
}
