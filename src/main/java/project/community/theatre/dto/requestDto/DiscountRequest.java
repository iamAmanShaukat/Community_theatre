package project.community.theatre.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AliasFor;
import project.community.theatre.dto.TicketType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRequest {
    private Map<String, TicketType> bands;
    @JsonProperty("isSocialClub")
    private boolean isSocialClub;
    private int totalTickets;
    private String day;
    private LocalDateTime showTime;
}