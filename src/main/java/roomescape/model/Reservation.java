package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(long id, String name, LocalDate date, LocalTime time) {

    private static final String IS_NULL_VALUE = "[EXCEPTION] 널 값은 입력될 수 없습니다.";

    public Reservation {
        Objects.requireNonNull(name, IS_NULL_VALUE);
        Objects.requireNonNull(date, IS_NULL_VALUE);
        Objects.requireNonNull(time, IS_NULL_VALUE);
    }
}
