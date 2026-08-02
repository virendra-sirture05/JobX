package com.project.referral.service.impl;

import com.project.referral.common.domain.ApplicationStatus;
import com.project.referral.common.dto.response.*;
import com.project.referral.common.exception.ApplicationException;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.CompanyApplicationFilterRequest;
import com.project.referral.dto.request.CreateApplicationRequest;
import com.project.referral.dto.request.UpdateApplicationStatusRequest;
import com.project.referral.dto.request.WithdrawApplicationRequest;
import com.project.referral.entity.Application;
import com.project.referral.entity.ApplicationNote;
import com.project.referral.entity.ApplicationScreening;
import com.project.referral.entity.ApplicationStatusHistory;
import com.project.referral.mapper.ApplicationMapper;
import com.project.referral.repository.ApplicationNoteRepository;
import com.project.referral.repository.ApplicationRepository;
import com.project.referral.repository.ApplicationScreeningRepository;
import com.project.referral.repository.ApplicationSpecification;
import com.project.referral.repository.ApplicationStatusHistoryRepository;
import com.project.referral.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationScreeningRepository screeningRepository;
    private final ApplicationStatusHistoryRepository historyRepository;
    private final ApplicationNoteRepository noteRepository;

    // ── Create ────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ApplicationResponse createApplication(Long candidateId,
                                                 CreateApplicationRequest req)
            throws ApplicationException {
        if (applicationRepository.existsByCandidateIdAndJobId(candidateId, req.getJobId())) {
            throw new ApplicationException("You have already applied for this job");
        }
        Long companyId = 1L;
        Long employeeId = 1L;

        // todo : fetch job
        // todo : fetch company
        // Fetch job to resolve companyId and employerId

        // Validate resume belongs to the candidate (security check only)


        Application application = ApplicationMapper.toEntity(req, candidateId,
               companyId, employeeId);

        application = applicationRepository.save(application);

        ApplicationStatusHistory initialHistory = ApplicationStatusHistory.builder()
                .application(application)
                .fromStatus(null)
                .toStatus(ApplicationStatus.PENDING)
                .changedByUserId(candidateId)
                .note("Application submitted")
                .build();
        historyRepository.save(initialHistory);


        return buildFullResponse(application);
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long id) throws ResourceNotFoundException {
        Application application = getApplicationEntity(id);
        return buildFullResponse(application);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getMyApplications(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId).stream()
                .map(this::buildFullResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId).stream()
                .map(this::buildFullResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsForCompany(
            Long userId,
            CompanyApplicationFilterRequest filter
    ) throws ResourceNotFoundException {

        //todo : fetch company Id
        Long companyId =1L;

        LocalDateTime from = filter.getAppliedFrom() != null
                ? filter.getAppliedFrom().atStartOfDay() : null;
        LocalDateTime to = filter.getAppliedTo() != null
                ? filter.getAppliedTo().atTime(LocalTime.MAX) : null;

        Sort sort = buildSort(filter.getSortBy());

        return applicationRepository.findAll(
                ApplicationSpecification.forCompanyWithFilters(
                        companyId,
                        filter.getJobId(),
                        filter.getStatus(),
                        filter.getSource(),
                        filter.getIsRead(),
                        filter.getIsStarred(),
                        from,
                        to,
                        filter.getAiShortlistStatus(),
                        filter.getMinAiScore()
                ), sort).stream()
                .map(this::buildFullResponse)
                .collect(Collectors.toList());
    }

    // ── Status update ─────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ApplicationResponse updateStatus(Long applicationId, Long employerId,
                                             UpdateApplicationStatusRequest req)
            throws ResourceNotFoundException, ApplicationException {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new ApplicationException("Cannot update status of a withdrawn application");
        }
        if (application.getStatus() == req.getStatus()) {
            throw new ApplicationException("Application is already in status: " + req.getStatus());
        }

        ApplicationStatus oldStatus = application.getStatus();
        application.setStatus(req.getStatus());
        application = applicationRepository.save(application);

        historyRepository.save(ApplicationStatusHistory.builder()
                .application(application)
                .fromStatus(oldStatus)
                .toStatus(req.getStatus())
                .changedByUserId(employerId)
                .note(req.getNote())
                .build());


        return buildFullResponse(application);
    }

    // ── Withdraw ──────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ApplicationResponse withdraw(Long applicationId, Long candidateId,
                                         WithdrawApplicationRequest req)
            throws ResourceNotFoundException, ApplicationException {
        Application application = getApplicationEntity(applicationId);
        assertCandidate(application, candidateId);

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new ApplicationException("Application is already withdrawn");
        }
        if (application.getStatus() == ApplicationStatus.HIRED) {
            throw new ApplicationException("Cannot withdraw an accepted offer");
        }

        ApplicationStatus oldStatus = application.getStatus();
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setWithdrawnAt(LocalDateTime.now());
        application.setWithdrawnReason(req.getReason());
        application = applicationRepository.save(application);

        historyRepository.save(ApplicationStatusHistory.builder()
                .application(application)
                .fromStatus(oldStatus)
                .toStatus(ApplicationStatus.WITHDRAWN)
                .changedByUserId(candidateId)
                .note(req.getReason())
                .build());

        return buildFullResponse(application);
    }

    // ── Tracking flags ────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ApplicationResponse markAsRead(Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        application.setIsRead(true);
        return buildFullResponse(applicationRepository.save(application));
    }

    @Override
    @Transactional
    public ApplicationResponse toggleStar(Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        application.setIsStarred(!application.getIsStarred());
        return buildFullResponse(applicationRepository.save(application));
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void deleteApplication(Long applicationId, Long candidateId)
            throws ResourceNotFoundException, ApplicationException {
        Application application = getApplicationEntity(applicationId);
        assertCandidate(application, candidateId);
        application.setStatus(ApplicationStatus.DELETED);
        applicationRepository.save(application);
    }

    // ── Internal ──────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Application getApplicationEntity(Long id) throws ResourceNotFoundException {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + id));
    }

    @Override
    @Transactional
    public void markScreeningsStaleForJob(Long jobId) {
        List<Long> applicationIds = applicationRepository.findByJobId(jobId)
                .stream().map(Application::getId).collect(Collectors.toList());
        if (applicationIds.isEmpty()) return;
        List<ApplicationScreening> screenings = screeningRepository.findByApplicationIdIn(applicationIds);
        screenings.forEach(s -> s.setIsStale(true));
        screeningRepository.saveAll(screenings);
    }

    // ── Private utilities ─────────────────────────────────────────────────────

    private void assertEmployer(Application application, Long employerId) throws ApplicationException {
        if (!application.getEmployerId().equals(employerId)) {
            throw new ApplicationException("You are not the employer for this application");
        }
    }

    private void assertCandidate(Application application, Long candidateId) throws ApplicationException {
        if (!application.getCandidateId().equals(candidateId)) {
            throw new ApplicationException("You are not the owner of this application");
        }
    }

    private Sort buildSort(String sortBy) {
        if ("AI_SCORE_DESC".equals(sortBy)) {
            return Sort.by(Sort.Order.desc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        } else if ("AI_SCORE_ASC".equals(sortBy)) {
            return Sort.by(Sort.Order.asc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }
        return Sort.by(Sort.Direction.DESC, "appliedAt");
    }

    private ApplicationResponse buildFullResponse(Application application) {

        //todo : fetch requiered data form respective service
        List<ApplicationNote> notes =
                noteRepository.findByApplicationIdOrderByCreatedAtDesc(application.getId());
        return ApplicationMapper.toResponse(application, JobSummaryResponse.builder().id(application.getJobId()).build(),notes, CompanySummaryResponse.builder().id(application.getCompanyId()).build(), UserResponse.builder().id(application.getCandidateId()).build() );
    }
}
