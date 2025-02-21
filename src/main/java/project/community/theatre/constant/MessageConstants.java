package project.community.theatre.constant;

public class MessageConstants {

    // General Application Constants
    public static final String APPLICATION_NAME = "GCT Tickets Backend";
    public static final String API_VERSION = "v1";

    // Error Messages
    public static final String ERROR_INVALID_FILE_FORMAT = "Unsupported file format. Supported formats are: jpg, jpeg, png, bmp, gif.";
    public static final String ERROR_FILE_SIZE_EXCEEDED = "File size exceeds the limit of 5MB.";
    public static final String ERROR_IMAGE_REQUIRED = "Image file is required.";
    public static final String ERROR_SEAT_ALREADY_LOCKED = "Seat is already locked.";
    public static final String ERROR_SEAT_NOT_AVAILABLE = "Seat is not available.";

    // Logging Messages
    public static final String LOG_MOVIE_ADDED_SUCCESSFULLY = "Movie added successfully: {}";
    public static final String LOG_SEATS_LOCKED_SUCCESSFULLY = "Seats locked successfully for show: {}";
    public static final String LOG_SEATS_CHECKED_AVAILABILITY = "Checked seat availability for show: {}";

    // Validation Messages
    public static final String VALIDATION_ID_CANNOT_BE_NULL = "ID cannot be null";
    public static final String VALIDATION_START_DATE_CANNOT_BE_NULL = "Start date cannot be null";
    public static final String VALIDATION_END_DATE_CANNOT_BE_NULL = "End date cannot be null";

}
