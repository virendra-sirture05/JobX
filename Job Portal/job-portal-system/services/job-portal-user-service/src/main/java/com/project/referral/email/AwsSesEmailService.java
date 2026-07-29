package com.project.referral.email;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SesV2Exception;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsSesEmailService implements EmailService {

    private final SesV2Client sesClient;

    private final EmailTemplateService emailTemplateService;

    @Value("${aws.ses.sender-email}")
    private String senderEmail;

    @Override
    public void sendVerificationOtp(
            String email,
            String otp
    ) {

        sendEmail(
                email,
                emailTemplateService.verificationOtpSubject(),
                emailTemplateService.verificationOtpBody(otp)
        );

    }

    @Override
    public void sendForgotPasswordOtp(
            String email,
            String otp
    ) {

        sendEmail(
                email,
                emailTemplateService.forgotPasswordSubject(),
                emailTemplateService.forgotPasswordBody(otp)
        );

    }

    private void sendEmail(
            String recipient,
            String subject,
            String body
    ) {

        try {

            Destination destination =
                    Destination.builder()
                            .toAddresses(recipient)
                            .build();

            Content subjectContent =
                    Content.builder()
                            .data(subject)
                            .build();

            Content bodyContent =
                    Content.builder()
                            .data(body)
                            .build();

            Body emailBody =
                    Body.builder()
                            .text(bodyContent)
                            .build();

            Message message =
                    Message.builder()
                            .subject(subjectContent)
                            .body(emailBody)
                            .build();

            EmailContent emailContent =
                    EmailContent.builder()
                            .simple(message)
                            .build();

            SendEmailRequest request =
                    SendEmailRequest.builder()
                            .fromEmailAddress(senderEmail)
                            .destination(destination)
                            .content(emailContent)
                            .build();

            sesClient.sendEmail(request);

            log.info(
                    "Email sent successfully to {}",
                    recipient
            );

        } catch (SesV2Exception ex) {

            log.error(
                    "Failed to send email to {} : {}",
                    recipient,
                    ex.awsErrorDetails().errorMessage(),
                    ex
            );

            throw ex;

        }

    }

}