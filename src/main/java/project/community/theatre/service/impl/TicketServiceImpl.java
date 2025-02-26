package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.*;
import project.community.theatre.repository.PaymentHistoryRepository;
import project.community.theatre.repository.TicketRepository;
import project.community.theatre.service.TicketService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    @Transactional
    @Override
    public TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId) {
        // Generate a unique ticket number
        String ticketNumber = UUID.randomUUID().toString();
    
        // Convert seat numbers to a comma-separated string
        String seatNumbersString = String.join(",", paymentRequest.getSeatNumbers());
    
        // Convert showTime from String to LocalDateTime
        LocalDateTime showTime = LocalDateTime.parse(paymentRequest.getShowTime());
    
        // Create and save the ticket
        TicketEntity ticket = TicketEntity.builder()
                .user(new UserEntity(paymentRequest.getUserId()))
                .event(new EventEntity(paymentRequest.getEventId()))
                .ticketNumber(ticketNumber)
                .totalPrice(paymentRequest.getAmount())
                .seatNumbers(seatNumbersString)
                .showTime(showTime)
                .status(TicketEntity.TicketStatus.BOOKED)
                .build();
        ticketRepository.save(ticket);
    
        // Save payment history
        PaymentHistoryEntity paymentHistory = PaymentHistoryEntity.builder()
                .user(new UserEntity(paymentRequest.getUserId()))
                .transactionId(transactionId)
                .amount(paymentRequest.getAmount())
                .paymentTime(LocalDateTime.now())
                .status(PaymentHistoryEntity.PaymentStatus.SUCCESS)
                .build();
        paymentHistoryRepository.save(paymentHistory);
    
        // Map the ticket entity to a response DTO
        return new TicketResponse(
                ticket.getId(),
                ticket.getTicketNumber(),
                ticket.getTotalPrice(),
                ticket.getSeatNumbers(),
                ticket.getShowTime(),
                ticket.getStatus().name()
        );
    }
}