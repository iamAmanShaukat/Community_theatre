package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.*;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.PaymentHistoryRepository;
import project.community.theatre.repository.TicketRepository;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.ProcessTicketAsync;
import project.community.theatre.service.TicketService;
import project.community.theatre.service.UserService;
import project.community.theatre.util.EmailService;
import project.community.theatre.util.PDFUtil;
import project.community.theatre.util.QRCodeUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    @Autowired
    UserService userService;

    @Autowired
    ProcessTicketAsync processTicketAsync;


    private final TicketRepository ticketRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId) {
        log.info("Generating and saving ticket :: transactionId: {}", transactionId);
        // Generate a unique ticket number
        String ticketId = UUID.randomUUID().toString();
    
        // Convert seat numbers to a comma-separated string
        String seatNumbersString = String.join(",", paymentRequest.getSeatNumbers());
    
        // Convert showTime from String to LocalDateTime
        LocalDateTime showTime = LocalDateTime.parse(paymentRequest.getShowTime());
    
        // Create and save the ticket
        TicketEntity ticket = TicketEntity.builder()
                .id(paymentRequest.getUserId())
                .user(userRepository.findUserById(paymentRequest.getUserId())
                        .orElse(new UserEntity(paymentRequest.getUserId())))
                .event(eventRepository.findEventById(paymentRequest.getEventId())
                        .orElse(new EventEntity(paymentRequest.getEventId())))
                .ticketNumber(ticketId)
                .totalPrice(paymentRequest.getPayableAmount())
                .seatNumbers(seatNumbersString)
                .showTime(showTime)
                .status(TicketEntity.TicketStatus.BOOKED)
                .build();
        log.info("Saving ticket: {}", ticket);
        ticketRepository.save(ticket);
    
        // Save payment history
        PaymentHistoryEntity paymentHistory = PaymentHistoryEntity.builder()
                .id(paymentRequest.getUserId())
                .user(new UserEntity(paymentRequest.getUserId()))
                .transactionId(transactionId)
                .amount(paymentRequest.getPayableAmount())
                .paymentTime(LocalDateTime.now())
                .status(PaymentHistoryEntity.PaymentStatus.SUCCESS)
                .build();
        log.info("Saving payment history: {}", paymentHistory);
        paymentHistoryRepository.save(paymentHistory);

        UserEntity user = userService.getUserById(paymentRequest.getUserId());
        // Start async ticket delivery process in a new thread
        processTicketAsync.processTicketDeliveryAsync(ticket, paymentRequest.getEmail());

        // Map the ticket entity to a response DTO
        return new TicketResponse(
                ticket.getTicketNumber(),
                ticket.getTotalPrice(),
                ticket.getSeatNumbers(),
                ticket.getShowTime().toString(),
                ticket.getEvent().getName(),
                LocalDateTime.now(),
                ticket.getStatus().name()
        );
    }
}