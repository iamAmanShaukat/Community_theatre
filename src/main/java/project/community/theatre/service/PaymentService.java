package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.model.PaymentEntity;

public interface PaymentService {
    PaymentEntity getPaymentById(Long paymentId);

    PaymentResponse processPayment(PaymentRequest paymentRequest);
}