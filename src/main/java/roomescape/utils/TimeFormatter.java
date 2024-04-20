package roomescape.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private TimeFormatter() {
    }

    public static String format(LocalTime time) {
        return TIME_FORMATTER.format(time);
    }
}
