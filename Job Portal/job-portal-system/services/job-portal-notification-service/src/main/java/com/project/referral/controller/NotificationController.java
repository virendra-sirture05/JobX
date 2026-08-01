package com.project.referral.controller;

import com.project.referral.services.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final EmailNotificationService emailNotificationService;

    @GetMapping("/sent")
    public String NotificationController() throws Exception {
        emailNotificationService.sendStatusChangedEmail();
        return "email sent";
    }
}
