package project.community.theatre.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
public class EmailService {
    private final SendGrid sendGrid;

    public EmailService(@Value("${sendgrid.api.key}") String apiKey) {
        this.sendGrid = new SendGrid(apiKey);
    }

    public void sendEmailWithPDF(String to, String name, byte[] pdfBytes) {
        log.info("Sending email with PDF attachment to {}", to);
        try {
            // Set up email details
            Email from = new Email("collegeonlineclass@gmail.com");
            String subject = "GCT Ticket booking confirmation";
            Email toEmail = new Email(to);
            Content content = new Content("text/plain", "Dear " + name + ",\n\nHere is your GCT ticket booking confirmation for the event");

            // Create mail object
            Mail mail = new Mail(from, subject, toEmail, content);

            // Add PDF attachment
            Attachments attachments = new Attachments();
            attachments.setContent(Base64.getEncoder().encodeToString(pdfBytes));
            attachments.setType("application/pdf");
            attachments.setFilename("ticket.pdf");
            attachments.setDisposition("attachment");
            mail.addAttachments(attachments);

            // Send the email
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Email sent successfully to {}", to);
            } else {
                log.error("Failed to send email to {}. Status: {}, Body: {}", to, response.getStatusCode(), response.getBody());
                throw new RuntimeException("Email sending failed with status: " + response.getStatusCode());
            }
        } catch (IOException e) {
            log.error("IOException sending email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send email due to I/O error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error sending email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Unexpected error while sending email: " + e.getMessage(), e);
        }
    }
}