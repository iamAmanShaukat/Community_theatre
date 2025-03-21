package project.community.theatre.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a ResponseEntity with an ErrorResponse and a NOT_FOUND status.
     *
     * @param ex The ResourceNotFoundException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a NOT_FOUND status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidImageException and returns a ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     *
     * @param ex The InvalidImageException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     */
    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidImageException(InvalidImageException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles PaymentFailedException and returns a ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     *
     * @param ex The PaymentFailedException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     */
    // Handle PaymentFailedException
    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorResponse> handlePaymentFailedException(PaymentFailedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserAlreadyExistsException and returns a ResponseEntity with a conflict status and an error message.
     *
     * @param ex The UserAlreadyExistsException that occurred.
     * @return A ResponseEntity with a conflict status and an error message.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles UserNotFoundException and returns a ResponseEntity with an unauthorized status and an error message.
     *
     * @param ex The UserNotFoundException that occurred.
     * @return A ResponseEntity with an unauthorized status and an error message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handles DiscountNotFoundException and returns a ResponseEntity with a not found status and an error message.
     *
     * @param ex The DiscountNotFoundException that occurred.
     * @return A ResponseEntity with a not found status and an error message.
     */
    @ExceptionHandler(DiscountNotFoundException.class)
    public ResponseEntity<?> handleDiscountNotFoundException(DiscountNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles BandNotFoundException and returns a ResponseEntity with a not found status and an error message.
     *
     * @param ex The BandNotFoundException that occurred.
     * @return A ResponseEntity with a not found status and an error message.
     */
    @ExceptionHandler(BandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBandNotFoundException(BandNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles generic exceptions and returns a ResponseEntity with an ErrorResponse and an internal server error status.
     *
     * @param ex The Exception that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and an internal server error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Overrides the handleMethodArgumentNotValid method to handle validation errors and returns a ResponseEntity with a bad request status and a map of field errors.
     *
     * @param ex The MethodArgumentNotValidException that occurred.
     * @param headers The HttpHeaders object.
     * @param status The HttpStatusCode object.
     * @param request The WebRequest object.
     * @return A ResponseEntity with a bad request status and a map of field errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}