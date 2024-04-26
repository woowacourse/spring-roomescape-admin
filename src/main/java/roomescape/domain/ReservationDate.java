package roomescape.domain;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public record ReservationDate(LocalDate value) {
    public static ReservationDate from(String date) {
        try {
            return new ReservationDate(LocalDate.parse(date));

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("%s 는 올바르지 않은 형식입니다 (EX:2023-03-10)", date), e);
        }
    }

    public String valueAsString() {
        return value.toString();
    }
}
