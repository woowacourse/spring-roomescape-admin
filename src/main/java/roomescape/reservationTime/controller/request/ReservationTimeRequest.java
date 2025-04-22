package roomescape.reservationTime.controller.request;

import java.time.LocalTime;
import roomescape.reservationTime.model.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime toEntity(final Long id) {
        return new ReservationTime(id, startAt);
    }
}
