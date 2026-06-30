package com.jobx.server.email;


import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    public String verificationOtpSubject() {
        return "Verify Your JobX Account";
    }

    public String forgotPasswordSubject() {
        return "Reset Your JobX Password";
    }

    public String verificationOtpBody(String otp) {

        return """
                Hello,

                Thank you for registering with JobX.

                Your verification OTP is:

                %s

                This OTP is valid for 5 minutes.

                If you did not request this verification, please ignore this email.

                Regards,
                JobX Team
                """.formatted(otp);

    }

    public String forgotPasswordBody(String otp) {

        return """
                Hello,

                We received a request to reset your JobX account password.

                Your OTP is:

                %s

                This OTP is valid for 5 minutes.

                If you did not request this, please ignore this email.

                Regards,
                JobX Team
                """.formatted(otp);

    }

}