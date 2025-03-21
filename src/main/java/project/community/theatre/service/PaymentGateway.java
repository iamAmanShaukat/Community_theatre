package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;

public interface PaymentGateway {
    /**
     * Processes a payment request and returns a response indicating the success or failure of the payment.
     *
     * @param request The PaymentRequest object containing payment details.
     * @return A PaymentResponse object containing the result of the payment processing.
     * @throws PaymentFailedException if the payment processing fails due to an error.
     */
    PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException;
}