package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;
import project.community.theatre.service.PaymentGateway;

import java.util.UUID;

@Service
@Slf4j
public class PaymentGatewayImpl implements PaymentGateway {

    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException {
        log.info("Processing payment: {}", request);
        try {
            Thread.sleep(1000); // Fake delay to mimic network call
            String transactionId = UUID.randomUUID().toString();
            double amount = request.getPayableAmount();

            return new PaymentResponse(Boolean.TRUE, "Payment processed successfully for " + amount, transactionId);
        } catch (InterruptedException e) {
            throw new PaymentFailedException("Payment simulation interrupted");
        }
    }
}