package roomescape.domain.reservation.dto;

import roomescape.domain.reservationtime.ReservationTime;

public record ReservationTimePayload(
    Long id,
    String startAt
) {

    public static ReservationTimePayload from(ReservationTime reservationTime) {
        return new ReservationTimePayload(reservationTime.getId(), reservationTime.getStartAt());
    }
}
