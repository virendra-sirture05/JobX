package com.project.referral.service.impl;

import com.project.referral.common.dto.response.ApplicationNoteResponse;
import com.project.referral.common.exception.ApplicationException;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddApplicationNoteRequest;
import com.project.referral.entity.Application;
import com.project.referral.entity.ApplicationNote;
import com.project.referral.mapper.ApplicationMapper;
import com.project.referral.repository.ApplicationNoteRepository;
import com.project.referral.service.ApplicationNoteService;
import com.project.referral.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationNoteServiceImpl implements ApplicationNoteService {

    private final ApplicationNoteRepository noteRepository;
    private final ApplicationService applicationService;

    // ── Add ───────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ApplicationNoteResponse addNote(Long applicationId, Long employerId,
                                            AddApplicationNoteRequest req)
            throws ResourceNotFoundException, ApplicationException {
        Application application = applicationService
                .getApplicationEntity(applicationId);

        assertEmployer(application, employerId);

        ApplicationNote note = ApplicationNote.builder()
                .application(application)
                .addedByUserId(employerId)
                .content(req.getContent())
                .build();

        ApplicationNoteResponse response = ApplicationMapper.toNoteResponse(noteRepository.save(note));
        return response;
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationNoteResponse> getNotesByApplication(Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException {
        Application application = applicationService.getApplicationEntity(applicationId);
        assertEmployer(application, employerId);

        return noteRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId)
                .stream()
                .map(ApplicationMapper::toNoteResponse)
                .collect(Collectors.toList());
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void deleteNote(Long noteId, Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException {
        Application application = applicationService.getApplicationEntity(applicationId);
        assertEmployer(application, employerId);

        ApplicationNote note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Note not found with id: " + noteId));

        if (!note.getApplication().getId().equals(applicationId)) {
            throw new ApplicationException(
                    "Note does not belong to application with id: " + applicationId);
        }

        noteRepository.delete(note);
    }

    // ── Private utilities ─────────────────────────────────────────────────────

    private void assertEmployer(Application application, Long employerId) throws ApplicationException {
        if (!application.getEmployerId().equals(employerId)) {
            throw new ApplicationException("You are not the employer for this application");
        }
    }
}
