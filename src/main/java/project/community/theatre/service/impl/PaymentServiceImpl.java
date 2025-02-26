package project.community.theatre.service.impl;

import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.model.PaymentEntity;
import project.community.theatre.service.PaymentService;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Simulate payment processing
        if (isValidPayment(paymentRequest)) {
            // Generate a dummy transaction ID
            String transactionId = UUID.randomUUID().toString();

            // Return a success response
            return new PaymentResponse(true, "Payment successful", transactionId);
        } else {
            return new PaymentResponse(false, "Payment failed: Invalid payment details", null);
        }
    }

    @Override
    public PaymentEntity getPaymentById(Long paymentId) {
        return null;
    }

    private boolean isValidPayment(PaymentRequest paymentRequest) {
        // Dummy validation logic
        return !paymentRequest.getCardNumber().startsWith("4000");
    }
}