package project.community.theatre.enums;

import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
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

    public static String getSupportedFormatsAsString() {
        return Stream.of(values())
                .map(ImageFormat::getFormat)
                .collect(Collectors.joining(", "));
    }
}