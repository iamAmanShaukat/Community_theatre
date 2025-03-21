package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;

public interface PaymentService {
    /**
     * Processes a payment request.
     *
     * @param paymentRequest The request containing payment details.
     * @return A PaymentResponse indicating the success or failure of the payment processing.
     */
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}