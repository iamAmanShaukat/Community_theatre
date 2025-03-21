package project.community.theatre.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides utility methods for converting date and time from ISO 8601 format to human-readable format.
 */
public class DateTimeConverter {
    /**
     * Converts a given ISO 8601 date-time to a human-readable date format.
     *
     * @param isoDateTime The ISO 8601 date-time to be converted.
     * @return A string representing the date in the format "MMMM dd, yyyy".
     */
    public static String getHumanReadableDate(LocalDateTime isoDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return isoDateTime.format(dateFormatter);
    }

    /**
     * Converts a given ISO 8601 date-time to a human-readable time format.
     *
     * @param isoDateTime The ISO 8601 date-time to be converted.
     * @return A string representing the time in the format "h:mm a".
     */
    // Function to return the time in human-readable format
    public static String getHumanReadableTime(LocalDateTime isoDateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return isoDateTime.format(timeFormatter);
    }

}
