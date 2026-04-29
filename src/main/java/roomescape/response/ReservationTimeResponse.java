package roomescape.response;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponse(Long id, LocalTime time) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getTime());
    }
}
