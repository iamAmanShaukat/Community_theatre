package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;

public interface PaymentGateway {
    PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException;
}