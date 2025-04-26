package roomescape.reservationTime.dto;

import roomescape.reservationTime.domain.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }
}
