package roomescape.reservationTime.controller.request;

import java.time.LocalTime;
import roomescape.reservationTime.model.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }
}
