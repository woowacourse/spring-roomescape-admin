package roomescape.controller.dto.response;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {

    public static ReservationTimeResponse fromReservationTime(ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getStartAt());
    }
}
