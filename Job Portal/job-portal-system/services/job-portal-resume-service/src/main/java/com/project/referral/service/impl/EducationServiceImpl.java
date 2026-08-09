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
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req)
            throws ResourceNotFoundException {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);
        return save(null, resume, req);
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
                                             AddEducationRequest req)
            throws ResourceNotFoundException {
        Education education = getEducation(educationId, resumeId);
        assertOwner(education.getResume(), candidateId, resumeId);
        return save(education, education.getResume(), req);
    }

    @Override
    @Transactional
    public void deleteEducation(Long educationId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException {
        Education education = getEducation(educationId, resumeId);
        assertOwner(education.getResume(), candidateId, resumeId);
        educationRepository.delete(education);
    }

    private EducationResponse save(Education education, Resume resume, AddEducationRequest req) {
        if (education == null) education = new Education();
        education.setResume(resume);
        education.setInstitutionName(req.getInstitutionName());
        education.setDegree(req.getDegree());
        education.setFieldOfStudy(req.getFieldOfStudy());
        education.setGrade(req.getGrade());
        education.setStartDate(req.getStartDate());
        education.setEndDate(Boolean.TRUE.equals(req.getIsCurrentlyStudying()) ? null : req.getEndDate());
        education.setIsCurrentlyStudying(Boolean.TRUE.equals(req.getIsCurrentlyStudying()));
        education.setDescription(req.getDescription());
        education.setDisplayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0);
        return ResumeMapper.toEducationResponse(educationRepository.save(education));
    }

    private Education getEducation(Long educationId, Long resumeId) throws ResourceNotFoundException {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found with id: " + educationId));
        if (!education.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException("Education not found with id: " + educationId);
        }
        return education;
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws ResourceNotFoundException {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }
    }
}
