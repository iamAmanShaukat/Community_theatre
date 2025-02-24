package project.community.theatre.constant;

public class AppConstants {

    // General Application Constants
    public static final String API_VERSION = "/v1";
    public static final String BASE_URL = "/api"+API_VERSION;

    // File Upload Constants
    public static final String UPLOAD_DIR = "uploads/images/";
    public static final long MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; // 5MB
    public static final String[] SUPPORTED_IMAGE_FORMATS = {"jpg", "jpeg", "png", "bmp", "gif"};

    // API Endpoints
    public static final String API_EVENT_ADD = "/api/" + API_VERSION + "/event/add-event";
    public static final String API_SEATS_LOCK = "/api/" + API_VERSION + "/seats/lock/{showId}";
    public static final String API_SEATS_CHECK = "/api/" + API_VERSION + "/seats/check/{showId}";

}
