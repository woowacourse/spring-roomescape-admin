package roomescape.domain.reservationtime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StartAt {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalTime value;

    private StartAt(LocalTime value) {
        this.value = value;
    }

    public static StartAt from(String value) {
        validateValue(value);
        return new StartAt(convertLocalDate(value));
    }

    private static LocalTime convertLocalDate(String value) {
        try {
            return LocalTime.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시작 시간 형식은 HH:mm 이어야 합니다.");
        }
    }

    private static void validateValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("시작 시간은 비어있을 수 없습니다.");
        }
    }

    public String toStringTime() {
        return value.format(TIME_FORMATTER);
    }
}
