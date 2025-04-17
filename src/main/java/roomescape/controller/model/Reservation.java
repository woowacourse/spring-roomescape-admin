package roomescape.controller.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
    Long id,
    String name,
    LocalDate date,
    LocalTime time
) {

    public Reservation {
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR]  예약 필수 정보가 누락되었습니다. name: " + name);
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR]  예약 필수 정보가 누락되었습니다. date: " + date);
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR]  예약 필수 정보가 누락되었습니다. time: " + time);
        }
    }

    public Reservation withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR]  예약 필수 정보가 누락되었습니다. id: " + id);
        }
        return new Reservation(id, name, date, time);
    }
}
