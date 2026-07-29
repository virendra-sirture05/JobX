package com.project.referral.service;

import com.project.referral.common.dto.response.ApplicationNoteResponse;
import com.project.referral.common.exception.ApplicationException;
import com.project.referral.common.exception.ResourceNotFoundException;
import com.project.referral.dto.request.AddApplicationNoteRequest;

import java.util.List;

public interface ApplicationNoteService {

    ApplicationNoteResponse addNote(
            Long applicationId, Long employerId,
                                     AddApplicationNoteRequest req)
            throws ResourceNotFoundException, ApplicationException;

    List<ApplicationNoteResponse> getNotesByApplication(
            Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException;

    void deleteNote(Long noteId, Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException;
}
