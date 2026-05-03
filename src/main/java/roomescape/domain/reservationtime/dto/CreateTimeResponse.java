package roomescape.domain.reservationtime.dto;

import java.time.LocalTime;
import roomescape.domain.reservationtime.ReservationTime;

public record CreateTimeResponse(
    Long id,
    LocalTime startAt
) {

    public static CreateTimeResponse from(ReservationTime reservationTime) {
        return new CreateTimeResponse(
            reservationTime.getId(),
            reservationTime.getStartAt()
        );
    }
}
