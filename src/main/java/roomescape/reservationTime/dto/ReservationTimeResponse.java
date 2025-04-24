package roomescape.reservationTime.dto;

import java.time.LocalTime;
import roomescape.reservationTime.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }
}
