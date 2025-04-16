package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public Reservation {
        validateNotNull(id, name, date, time);
        validateNameLength(name);
    }

    private void validateNotNull(final Long id, final String name, final LocalDate date, final LocalTime time) {
        if (id == null || name == null || date == null || time == null) {
            throw new IllegalArgumentException("모든 값들이 존재해야 합니다.");
        }
    }

    private void validateNameLength(final String name) {
        if (name.length() > 5) {
            throw new IllegalArgumentException("이름은 5자를 넘길 수 없습니다.");
        }
    }
}
