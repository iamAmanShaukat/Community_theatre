package project.community.theatre.exceptionHandler;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException(String message) {
        super(message);
    }
}