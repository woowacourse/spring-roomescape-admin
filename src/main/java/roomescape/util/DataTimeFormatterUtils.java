package roomescape.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DataTimeFormatterUtils {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String formatToHourMinute(LocalTime localTime) {
        return TIME_FORMATTER.format(localTime);
    }
}
