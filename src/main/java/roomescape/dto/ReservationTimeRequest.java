package roomescape.dto;

import roomescape.reservation.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
