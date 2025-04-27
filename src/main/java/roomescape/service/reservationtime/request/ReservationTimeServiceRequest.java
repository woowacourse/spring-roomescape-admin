package roomescape.service.reservationtime.request;

import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record ReservationTimeServiceRequest(LocalTime startAt) {

    public ReservationTime toTime() {
        return ReservationTime.ofWithoutId(this.startAt);
    }

}
