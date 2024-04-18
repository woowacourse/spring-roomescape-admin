package roomescape.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeFormatter {
    private static final DateTimeFormatter ISO_LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_WITHOUT_SECOND_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private CustomDateTimeFormatter() {
    }

    public static LocalDate getLocalDate(final String date) {
        return LocalDate.parse(date, ISO_LOCAL_DATE_FORMATTER);
    }

    public static LocalTime getLocalTime(final String time) {
        return LocalTime.parse(time, TIME_WITHOUT_SECOND_FORMATTER);
    }

    public static String getFormattedDate(final LocalDate date) {
        return date.format(ISO_LOCAL_DATE_FORMATTER);
    }

    public static String getFormattedTime(final LocalTime time) {
        return time.format(TIME_WITHOUT_SECOND_FORMATTER);
    }
}
