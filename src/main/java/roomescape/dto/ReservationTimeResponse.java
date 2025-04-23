package roomescape.dto;

import java.time.LocalTime;

import roomescape.model.ReservationTime;

public record ReservationTimeResponse(
    Long id,
    LocalTime startAt
) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }
}
