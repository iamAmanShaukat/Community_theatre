package project.community.theatre.Converter;


import project.community.theatre.Dto.TicketDto;
import project.community.theatre.Model.TicketEntity;

public class TicketConverter {

    public static TicketDto convertEntityToDto(TicketEntity ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .amount(ticket.getAmount())
                .alotedSeats(ticket.getAllotedSeats()).build();
    }
}
