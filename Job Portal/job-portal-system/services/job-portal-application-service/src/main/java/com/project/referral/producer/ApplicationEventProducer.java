package com.project.referral.producer;

import com.project.referral.client.CompanyClient;
import com.project.referral.client.JobClient;
import com.project.referral.client.UserClient;
import com.project.referral.common.domain.ApplicationStatus;
import com.project.referral.common.dto.response.CompanySummaryResponse;
import com.project.referral.common.dto.response.JobSummaryResponse;
import com.project.referral.common.dto.response.UserResponse;
import com.project.referral.common.event.ApplicationStatusChangedEvent;
import com.project.referral.entity.Application;
import com.project.referral.event.ApplicationSubmittedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationEventProducer {

//    private static final String TOPIC = "application-topic";
    public static final String TOPIC_STATUS_CHANGED = "application.status.changed";


//    public static final String TOPIC_NOTE_ADDED = "application.note.added";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserClient userClient;
    private final JobClient jobClient;
    private final CompanyClient companyClient;


//    public void publishApplicationSubmittedEvent(
//            ApplicationSubmittedEvent event) {
//
//        kafkaTemplate.send(TOPIC, event);
//
//        System.out.println("------------------------------------");
//        System.out.println("Event Published Successfully");
//        System.out.println(event);
//        System.out.println("------------------------------------");
//    }

    public void publishStatusChanged(Application app,
                                     ApplicationStatus oldStatus,
                                     String note) {
        try {
            UserResponse candidate = userClient.getUserById(app.getCandidateId());
//            JobSummaryResponse job = jobClient.getJobSummaryById(app.getJobId());
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
                    .jobTitle("fsfsfs")
                    .companyName(company.getName())
                    .changedAt(LocalDateTime.now())
                    .build();

            kafkaTemplate.send(TOPIC_STATUS_CHANGED, event);
            System.out.println(("Published status-changed event for application {}"+ event + " dad " +app.getId()));
        } catch (Exception e) {
            System.out.println(("Failed to publish status-changed event for application {}"+ app.getId()+" "+ e));
        }
    }

}