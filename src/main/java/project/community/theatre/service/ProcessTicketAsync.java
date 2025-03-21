package project.community.theatre.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.model.UserEntity;
import project.community.theatre.util.EmailService;
import project.community.theatre.util.PDFUtil;
import project.community.theatre.util.QRCodeUtil;

/**
 * This service class is responsible for asynchronously processing ticket delivery.
 * It creates a new thread to handle the ticket delivery process for a given ticket.
 * The process includes generating a QR code, creating a PDF, sending an email with the PDF,
 * and handling any exceptions that may occur during the process.
 */
@Service
@Slf4j
public class ProcessTicketAsync {

    /**
     * The email service used to send emails with PDF attachments.
     */
    @Autowired
    EmailService emailService;

    /**
     * Asynchronously processes the ticket delivery for the given ticket and provided email.
     *
     * @param ticket The ticket entity for which the delivery needs to be processed.
     * @param providedEmail The email address provided by the user. If null or empty,
     *                      the email address associated with the ticket's user will be used.
     */
    public void processTicketDeliveryAsync(TicketEntity ticket, String providedEmail) {
        new Thread(() -> {
            int maxAttempts = 3;
            int attempt = 1;
            long delay = 2000;

            while (attempt <= maxAttempts) {
                String ticketId = ticket.getTicketNumber();
                try {
                    log.info("Processing ticket delivery for ticketId: {}, attempt: {}", ticketId, attempt);

                    byte[] qrTicket = QRCodeUtil.generateTicketQRCode(ticketId, 200, 200);
                    byte[] pdfBytes = PDFUtil.createPDF(qrTicket, ticket);
                    String email = ObjectUtils.isNotEmpty(providedEmail)? providedEmail : ticket.getUser().getEmail();
                    emailService.sendEmailWithPDF(email, ticket.getUser().getName(), pdfBytes);

                    log.info("Successfully delivered ticket for ticketId: {}", ticketId);
                    break;

                } catch (Exception e) {
                    log.error("Failed to process ticket delivery for ticketId: {}, attempt: {}",
                            ticketId, attempt, e);

                    if (attempt == maxAttempts) {
                        log.error("All attempts failed for ticketId: {}", ticketId);
                        break;
                    }

                    try {
                        Thread.sleep(delay);
                        delay *= 2; // Exponential backoff
                    } catch (InterruptedException ie) {
                        log.error("Sleep interrupted for ticketId: {}", ticketId, ie);
                        Thread.currentThread().interrupt();
                        break;
                    }

                    attempt++;
                }
            }
        }).start();
    }
}
