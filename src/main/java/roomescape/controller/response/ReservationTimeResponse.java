package roomescape.controller.response;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {

    public static ReservationTimeResponse from(final Long id, final ReservationTime reservationTime) {
        return new ReservationTimeResponse(id, reservationTime.getStartAt());
    }
}
