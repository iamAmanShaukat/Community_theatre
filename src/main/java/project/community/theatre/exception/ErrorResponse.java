package project.community.theatre.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String details;

    public ErrorResponse(LocalDateTime timestamp, int status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }
}