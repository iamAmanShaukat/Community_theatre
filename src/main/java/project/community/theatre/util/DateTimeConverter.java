package project.community.theatre.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
    // Function to return the date in human-readable format
    public static String getHumanReadableDate(LocalDateTime isoDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return isoDateTime.format(dateFormatter);
    }

    // Function to return the time in human-readable format
    public static String getHumanReadableTime(LocalDateTime isoDateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return isoDateTime.format(timeFormatter);
    }

}
