package project.community.theatre.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class BookTicketRequestDto {
    Set<String> requestedSeat;
    int userId;
    int showId;
    List<String> seatNumbers;
    float totalAmount;
}
