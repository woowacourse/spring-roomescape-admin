package roomescape.dto;

import java.time.LocalDate;
import java.util.Objects;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    private static final int NAME_LENGTH_MIN = 2;
    private static final int NAME_LENGTH_MAX = 10;

    public ReservationRequest {
        validateName(name);
        validateDate(date);
        validateTimeId(timeId);
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

    private void validateTimeId(Long timeId) {
        Objects.requireNonNull(timeId);
    }
}
