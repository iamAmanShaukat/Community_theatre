package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;

public interface TicketService {

    /**
     * Generates a ticket for the given payment request and saves it to the database.
     * This method also locks the booked seats and records the payment history.
     * It initiates an asynchronous process to deliver the ticket via email.
     *
     * @param paymentRequest The details of the payment and booking.
     * @param transactionId The unique identifier for the transaction.
     * @return A TicketResponse containing the details of the generated ticket.
     */
    @Transactional
    TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId);

    /**
     * Retrieves the ticket details for a given ticket number.
     *
     * @param ticketNumber the ticket number to fetch details for
     * @return a TicketResponse containing the ticket details. If the ticket is not found, a 404 response is returned.
     */
    TicketResponse getTicketDetails(String ticketNumber);
}