package com.gopi.job.services;


import com.gopi.job.event.ApplicationStatusChangedEvent;
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

    public void sendStatusChangedEmail(ApplicationStatusChangedEvent event) throws Exception {
        try {
            String subject = "Application update: " + event.getJobTitle() + " at " + event.getCandidateName();
            String body = buildStatusChangeHtml(event);
            String candidateEmail = event.getCandidateEmail();

            sendEmail(candidateEmail, subject, body);

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    private void sendEmail(
            String candidateEmail,
            String subject,
            String body
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                true,
                "UTF-8"
        );

        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(candidateEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);

        mailSender.send(mimeMessage);
    }

    private String buildStatusChangeHtml(ApplicationStatusChangedEvent event) {
        return "<h1>Application status changed" + event.getNewStatus() + "</h1>";
    }
}
