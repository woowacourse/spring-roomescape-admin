package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        LocalTime time
) {
    private static final int NAME_LENGTH_MIN = 2;
    private static final int NAME_LENGTH_MAX = 10;
    private static final int RESERVATION_TIME_MIN = 9;
    private static final int RESERVATION_TIME_MAX = 20;

    public ReservationRequestDto {
        validateName(name);
        validateDate(date);
        validateTime(time);
        validateDateTime(date, time);
    }

    public static void validateName(String name) {
        Objects.requireNonNull(name);
        if (name.length() < NAME_LENGTH_MIN || NAME_LENGTH_MAX < name.length()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateDate(LocalDate date) {
        Objects.requireNonNull(date);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException();
        }
    }

    private void validateTime(LocalTime time) {
        Objects.requireNonNull(time);
        if (time.isBefore(LocalTime.of(RESERVATION_TIME_MIN, 0))
                || time.isAfter(LocalTime.of(RESERVATION_TIME_MAX, 0))) {
            throw new IllegalArgumentException();
        }
    }

    private void validateDateTime(LocalDate date, LocalTime time) {
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        if (localDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException();
        }
    }
}
