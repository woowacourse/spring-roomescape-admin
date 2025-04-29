package roomescape.reservation.web.dto;

import java.time.LocalTime;
import roomescape.reservation.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
