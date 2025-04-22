package roomescape.admin.controller.dto;

import java.time.LocalTime;
import roomescape.admin.domain.ReservationTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
