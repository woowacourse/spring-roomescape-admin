package roomescape.util;

import roomescape.domain.ReservationTime;
import roomescape.controller.ReservationTimeResponse;

public class ReservationTimeMapper {

    public static ReservationTimeResponse toResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.id(),
                reservationTime.startAt()
        );
    }
}
