package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Date {

    private final LocalDate value;

    private Date(LocalDate value) {
        this.value = value;
    }

    public static Date from(String value) {
        validateValue(value);
        return new Date(convertLocalDate(value));
    }

    private static LocalDate convertLocalDate(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("예약 날짜 형식은 '2024-04-02'이어야 합니다.");
        }
    }

    private static void validateValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("예약 날짜는 비어있을 수 없습니다.");
        }
    }

    public String toStringDate() {
        return value.toString();
    }
}
