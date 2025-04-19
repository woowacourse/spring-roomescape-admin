package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(long id, String name, LocalDate date, LocalTime time) {

    private static final String INVALID_INPUT = "[EXCEPTION] 널 값은 입력될 수 없습니다.";

    public Reservation {
        Objects.requireNonNull(name, INVALID_INPUT);
        Objects.requireNonNull(date, INVALID_INPUT);
        Objects.requireNonNull(time, INVALID_INPUT);
    }
}
