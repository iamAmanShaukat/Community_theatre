package project.community.theatre.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.model.UserEntity;
import project.community.theatre.util.EmailService;
import project.community.theatre.util.PDFUtil;
import project.community.theatre.util.QRCodeUtil;

@Service
@Slf4j
public class ProcessTicketAsync {

    @Autowired
    EmailService emailService;

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
                    String email = providedEmail != null ? providedEmail : ticket.getUser().getEmail();
                    emailService.sendEmailWithPDF(email, ticket.getUser().getName(), pdfBytes);

                    log.info("Successfully delivered ticket for ticketId: {}", ticketId);
                    break;

                } catch (Exception e) {
                    log.error("Failed to process ticket delivery for ticketId: {}, attempt: {}",
                            ticketId, attempt, e);

                    if (attempt == maxAttempts) {
                        log.error("All attempts failed for ticketId: {}", ticketId);
                        // Here you could add fallback logic like notifying an admin
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
