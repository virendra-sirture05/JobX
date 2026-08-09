package com.project.referral.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSubmittedEvent {

    private Long applicationId;

    private String candidateName;

    private String candidateEmail;

    private String jobTitle;

}