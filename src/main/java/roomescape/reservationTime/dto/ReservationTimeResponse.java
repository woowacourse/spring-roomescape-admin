package roomescape.reservationTime.dto;

import java.time.LocalTime;
import roomescape.reservationTime.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {
    public static ReservationTimeResponse from(ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getStartAt());
    }
}
