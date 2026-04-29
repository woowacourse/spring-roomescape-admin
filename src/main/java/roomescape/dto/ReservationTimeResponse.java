package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(long id, String startAt) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }
}
