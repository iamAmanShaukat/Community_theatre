package project.community.theatre.exception;

public class DiscountNotFoundException extends RuntimeException {
    public DiscountNotFoundException(String message) {
        super(message);
    }
}