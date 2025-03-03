package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;

public interface TicketService {

    @Transactional
    TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId);

    TicketResponse getTicketDetails(String ticketNumber);
}