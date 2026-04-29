package roomescape.domain.reservationtime.dto;

import roomescape.domain.reservationtime.ReservationTime;

public record ReservationTimeResponse(
    Long id,
    String startAt
) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
            reservationTime.getId(),
            reservationTime.getStartAt()
        );
    }
}
