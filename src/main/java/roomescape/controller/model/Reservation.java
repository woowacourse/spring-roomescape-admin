package roomescape.controller.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
    Long id,
    String name,
    LocalDate date,
    LocalTime time
) {

    public static final String ERROR_MESSAGE_FORMAT = "[ERROR]  예약 필수 정보가 누락되었습니다. %s: %s";

    public Reservation {
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, "name", name));
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, "date", date));
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, "time", time));
        }
    }

    public Reservation withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, "id", id));
        }
        return new Reservation(id, name, date, time);
    }
}
