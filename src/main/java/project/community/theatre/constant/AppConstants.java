package project.community.theatre.constant;

/**
 * This class contains constant values used throughout the application.
 * It includes API versioning, base URL, and file upload size limits.
 */
public class AppConstants {

    // General Application Constants
    public static final String API_VERSION = "/v1";
    public static final String BASE_URL = "/api"+API_VERSION;

    // File Upload Constants
    public static final long MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; // 5MB
    public static final int IMAGE_WIDTH = 800;
    public static final int IMAGE_HEIGHT = 600;
    public static final double IMAGE_QUALITY = 1.0;

}
