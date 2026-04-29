package roomescape.domain.reservationtime.dto;

import roomescape.domain.reservationtime.ReservationTime;

public record CreateTimeResponse(
    Long id,
    String startAt
) {

    public static CreateTimeResponse from(ReservationTime reservationTime) {
        return new CreateTimeResponse(
            reservationTime.getId(),
            reservationTime.getStartAt()
        );
    }
}
