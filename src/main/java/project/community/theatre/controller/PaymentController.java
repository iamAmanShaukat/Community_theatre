package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.UserEntity;
import project.community.theatre.service.PaymentService;
import project.community.theatre.service.TicketService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final TicketService ticketService;

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        // Process the payment
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

        if (!paymentResponse.isSuccess()) {
            return ResponseEntity.badRequest().body(paymentResponse);
        }

        // Generate and save the ticket
        TicketResponse ticketResponse = ticketService.generateAndSaveTicket(paymentRequest, paymentResponse.getTransactionId());

        return ResponseEntity.ok(ticketResponse);
    }
}