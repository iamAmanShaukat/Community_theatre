package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.service.PaymentGateway;
import project.community.theatre.service.PaymentService;

import java.util.UUID;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentGateway paymentGateway;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Simulate payment processing
        if (isValidPayment(paymentRequest)) {
            return paymentGateway.processPayment(paymentRequest);
        } else {
            String transactionId = UUID.randomUUID().toString();
            log.error("Invalid payment request: {} :: transactionId: {}", paymentRequest, transactionId);
            return new PaymentResponse(false, "Payment failed", transactionId);
        }
    }

    private boolean isValidPayment(PaymentRequest paymentRequest) {
        // Dummy validation logic
        if (paymentRequest.getPayableAmount() <= 0 || paymentRequest.getPaymentDetails().getCardNumber().startsWith("4000")) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}