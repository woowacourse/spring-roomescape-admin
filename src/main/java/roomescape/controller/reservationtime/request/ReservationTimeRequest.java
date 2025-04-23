package roomescape.controller.reservationtime.request;

import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime toTime() {
        return ReservationTime.of(this.startAt);
    }

}
