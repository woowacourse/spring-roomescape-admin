package roomescape.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// TODO: DateTimeFormatter 고민하기
public class CustomDateTimeFormatter {
    public static LocalDateTime getLocalDateTime(final String date, final String time) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

        return LocalDateTime.of(localDate, localTime);
    }

    public static String getFormattedDate(final LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getFormattedTime(final LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
