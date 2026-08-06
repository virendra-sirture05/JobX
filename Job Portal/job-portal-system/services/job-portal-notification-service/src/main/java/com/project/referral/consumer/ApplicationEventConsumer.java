package com.project.referral.consumer;

import com.project.referral.common.event.ApplicationStatusChangedEvent;
import com.project.referral.services.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationEventConsumer {

    private final EmailNotificationService emailService;

    @KafkaListener(
            topics = "application.status.changed",
            groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleStatusChanged(ApplicationStatusChangedEvent event) {

        System.out.println("=================================");
        System.out.println("MESSAGE RECEIVED");
        System.out.println(event);
        System.out.println("=================================");

        emailService.sendStatusChangedEmail(event);
    }
}