package roomescape.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeFormatUtils {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeFormatUtils() {
    }

    public static LocalDate formatDateFrom(String input) {
        return LocalDate.parse(input, DATE_FORMATTER);
    }

    public static LocalTime formatTimeFrom(String input) {
        return LocalTime.parse(input, TIME_FORMATTER);
    }
}
