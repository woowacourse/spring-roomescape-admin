package roomescape.admin.reservation.controller.dto.request;

import java.time.LocalTime;
import roomescape.admin.reservation.entity.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
