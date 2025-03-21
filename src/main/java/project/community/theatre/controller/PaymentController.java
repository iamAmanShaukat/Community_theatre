package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.service.PaymentService;
import project.community.theatre.service.TicketService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final TicketService ticketService;

    /**
     * Processes a payment request and generates a ticket if the payment is successful.
     *
     * @param paymentRequest The request containing payment details.
     * @return A ResponseEntity containing the generated ticket if the payment is successful, otherwise a bad request response with the payment response.
     */
    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Received payment request: {}", paymentRequest);
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