package roomescape.entity;

import java.time.LocalTime;

public record ReservationTime(Long id, LocalTime startAt) {

    public ReservationTime assignId(Long id) {
        return new ReservationTime(id, startAt);
    }
}
