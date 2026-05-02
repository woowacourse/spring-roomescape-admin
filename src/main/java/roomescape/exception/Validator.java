package roomescape.exception;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Validator {

    public static void validateDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 아닙니다.");
        }
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }

    public static void validateTime(String time) {
        if (!time.matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 아닙니다.");
        }

        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 시간입니다.");
        }
    }
}
