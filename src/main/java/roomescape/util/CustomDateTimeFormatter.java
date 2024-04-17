package roomescape.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeFormatter {
    private static final DateTimeFormatter ISO_LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_WITHOUT_SECOND_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private CustomDateTimeFormatter() {
    }

    public static LocalDateTime getLocalDateTime(final String date, final String time) {
        LocalDate localDate = LocalDate.parse(date, ISO_LOCAL_DATE_FORMATTER);
        LocalTime localTime = LocalTime.parse(time, TIME_WITHOUT_SECOND_FORMATTER);

        return LocalDateTime.of(localDate, localTime);
    }

    public static String getFormattedDate(final LocalDateTime dateTime) {
        return dateTime.format(ISO_LOCAL_DATE_FORMATTER);
    }

    public static String getFormattedTime(final LocalDateTime dateTime) {
        return dateTime.format(TIME_WITHOUT_SECOND_FORMATTER);
    }
}
