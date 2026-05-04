package roomescape.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public final class Parser {
    public static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }

    public static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 시간입니다.");
        }
    }
}
