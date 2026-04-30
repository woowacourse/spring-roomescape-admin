package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(long id, String startAt) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}
