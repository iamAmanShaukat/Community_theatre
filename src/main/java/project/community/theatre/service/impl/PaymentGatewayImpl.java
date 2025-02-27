package project.community.theatre.service.impl;

import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;
import project.community.theatre.service.PaymentGateway;

import java.util.UUID;

@Service
public class PaymentGatewayImpl implements PaymentGateway {

    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException {
        try {
            Thread.sleep(1000); // Fake delay to mimic network call
            String transactionId = UUID.randomUUID().toString();
            double amount = request.getAmount();

            return new PaymentResponse(Boolean.TRUE, "Payment processed successfully for " + amount, transactionId);
        } catch (InterruptedException e) {
            throw new PaymentFailedException("Payment simulation interrupted");
        }
    }
}