package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(Long id, LocalTime startAt) {

    public ReservationTime {
        validateStartAt(startAt);
    }

    void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("startAt cannot be null");
        }
    }
}
