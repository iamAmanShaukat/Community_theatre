package project.community.theatre.exception;

public class BandNotFoundException extends RuntimeException {
    public BandNotFoundException(String message) {
        super(message);
    }
}