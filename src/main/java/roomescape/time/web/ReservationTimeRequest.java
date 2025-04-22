package roomescape.time.web;

import java.time.LocalTime;
import roomescape.time.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
