package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {

    @Transactional
    TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId);
}