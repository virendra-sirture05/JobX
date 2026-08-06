package com.project.referral.services;

import com.project.referral.common.event.ApplicationStatusChangedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendStatusChangedEmail(ApplicationStatusChangedEvent event) {

        try {

            String subject = "Application Update - " + event.getJobTitle();

            String body = buildStatusChangeHtml(event);

            sendEmail(
                    event.getCandidateEmail(),
                    subject,
                    body
            );

            System.out.println("==================================");
            System.out.println("EMAIL SENT SUCCESSFULLY");
            System.out.println("To : " + event.getCandidateEmail());
            System.out.println("==================================");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendEmail(String to,
                           String subject,
                           String body) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(mimeMessage);
    }

    private String buildStatusChangeHtml(ApplicationStatusChangedEvent event) {

        return """
                <!DOCTYPE html>
                <html>
                <body style="font-family:Arial;background:#f5f5f5;padding:20px;">

                <div style="max-width:600px;margin:auto;background:white;
                            border-radius:10px;padding:30px;">

                    <h2 style="color:#2563eb;">
                        Application Status Updated
                    </h2>

                    <p>Hello <b>%s</b>,</p>

                    <p>
                        Your application for
                        <b>%s</b>
                        at
                        <b>%s</b>
                        has been updated.
                    </p>

                    <hr>

                    <h3>
                        New Status :
                        <span style="color:green;">%s</span>
                    </h3>

                    <p><b>Employer Note :</b></p>

                    <p>%s</p>

                    <br>

                    <p>
                        Login to Job Portal to view complete application details.
                    </p>

                    <br>

                    <p>
                        Regards,<br>
                        <b>Job Portal Team ❤️</b>
                    </p>

                </div>

                </body>
                </html>
                """.formatted(
                event.getCandidateName(),
                event.getJobTitle(),
                event.getCompanyName(),
                event.getNewStatus(),
                event.getNote() == null ? "No additional note." : event.getNote()
        );

    }

}