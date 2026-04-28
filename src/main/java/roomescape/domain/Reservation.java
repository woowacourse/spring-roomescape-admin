package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(String name, LocalDate date, LocalTime time) {
    public Reservation {
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    public boolean isSameDateTime(Reservation reservation) {
        return date.equals(reservation.date()) && time.equals(reservation.time());
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 이름은 필수 값입니다.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름 형식이 올바르지 않습니다.");
        }
    }

    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 날짜는 필수 값입니다.");
        }
    }

    private static void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 필수 값입니다.");
        }
    }

}
