package roomescape.entity;

import java.time.LocalTime;

public record ReservationTime(Long id, LocalTime startAt) {

    public ReservationTime(LocalTime startAt) {
        this(null, startAt);
    }
}
