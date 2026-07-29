package com.project.referral.event;

import com.project.referral.client.CompanyClient;
import com.project.referral.client.JobClient;
import com.project.referral.client.UserClient;
import com.project.referral.common.domain.ApplicationStatus;
import com.project.referral.common.dto.response.CompanySummaryResponse;
import com.project.referral.common.dto.response.JobSummaryResponse;
import com.project.referral.common.dto.response.UserResponse;
import com.project.referral.common.event.ApplicationNoteAddedEvent;
import com.project.referral.common.event.ApplicationStatusChangedEvent;
import com.project.referral.model.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventPublisher {

    public static final String TOPIC_STATUS_CHANGED = "application.status.changed";
    public static final String TOPIC_NOTE_ADDED = "application.note.added";
    //TODO:ADD KAFKATEMPLATE
  //  private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserClient userClient;
    private final JobClient jobClient;
    private final CompanyClient companyClient;

    public void publishStatusChanged(Application app,
                                     ApplicationStatus oldStatus,
                                     String note) {
        try {
            UserResponse candidate = userClient.getUserById(app.getCandidateId());
            JobSummaryResponse job = jobClient.getJobSummaryById(app.getJobId());
            CompanySummaryResponse company = companyClient.getCompanySummaryById(
                    app.getCompanyId());

            ApplicationStatusChangedEvent event = ApplicationStatusChangedEvent.builder()
                    .applicationId(app.getId())
                    .candidateId(app.getCandidateId())
                    .candidateEmail(candidate.getEmail())
                    .candidateName(candidate.getFullName())
                    .oldStatus(oldStatus)
                    .newStatus(app.getStatus())
                    .note(note)
                    .jobTitle(job.getTitle())
                    .companyName(company.getName())
                    .changedAt(LocalDateTime.now())
                    .build();
            throw new RuntimeException("kafka is not integrated yet");
            //TODO:ADD KAFKATEMPLATE
//            kafkaTemplate.send(TOPIC_STATUS_CHANGED,
//                    String.valueOf(app.getId()), event);
         //   log.info("Published status-changed event for application {}", app.getId());
        } catch (Exception e) {
//            log.error("Failed to publish status-changed event for application {}", app.getId(), e);
            log.error(e.getMessage());
        }
    }

    public void publishNoteAdded(Application app) {
        try {
            UserResponse candidate = userClient.getUserById(app.getCandidateId());
            JobSummaryResponse job = jobClient.getJobSummaryById(app.getJobId());
            CompanySummaryResponse company = companyClient.getCompanySummaryById(app.getCompanyId());

            ApplicationNoteAddedEvent event = ApplicationNoteAddedEvent.builder()
                    .applicationId(app.getId())
                    .candidateId(app.getCandidateId())
                    .candidateEmail(candidate.getEmail())
                    .candidateName(candidate.getFullName())
                    .jobTitle(job.getTitle())
                    .companyName(company.getName())
                    .addedAt(LocalDateTime.now())
                    .build();

          //TODO  uncomment this  kafkaTemplate.send(TOPIC_NOTE_ADDED, String.valueOf(app.getId()), event);
            log.info("Published note-added event for application {}", app.getId());
        } catch (Exception e) {
            log.error("Failed to publish note-added event for application {}", app.getId(), e);
        }
    }
}
