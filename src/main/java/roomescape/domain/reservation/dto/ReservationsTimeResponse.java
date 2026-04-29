package roomescape.domain.reservation.dto;

import roomescape.domain.reservationtime.ReservationTime;

public record ReservationsTimeResponse(
    Long id,
    String startAt
) {

    public static ReservationsTimeResponse from(ReservationTime reservationTime) {
        return new ReservationsTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
