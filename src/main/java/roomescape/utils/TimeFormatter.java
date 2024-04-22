package roomescape.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private TimeFormatter() {
    }

    public static String format(LocalDate date) {
        return DATE_FORMATTER.format(date);
    }

    public static String format(LocalTime time) {
        return TIME_FORMATTER.format(time);
    }
}
