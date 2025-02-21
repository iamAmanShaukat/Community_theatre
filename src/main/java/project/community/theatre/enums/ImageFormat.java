package project.community.theatre.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ImageFormat {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    BMP("bmp"),
    GIF("gif");

    private final String format;

    ImageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    /**
     * Checks if a given format is supported.
     *
     * @param format The file format to check (case-insensitive).
     * @return True if the format is supported, false otherwise.
     */
    public static boolean isSupported(String format) {
        if (format == null || format.isEmpty()) {
            return false;
        }
        String lowerCaseFormat = format.toLowerCase();
        for (ImageFormat imageFormat : values()) {
            if (imageFormat.getFormat().equals(lowerCaseFormat)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a comma-separated list of supported formats.
     *
     * @return A string containing all supported formats separated by commas.
     */
    public static String getSupportedFormatsAsString() {
        return Stream.of(values())
                .map(ImageFormat::getFormat)
                .collect(Collectors.joining(", "));
    }
}