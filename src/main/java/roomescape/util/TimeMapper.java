package roomescape.util;

import roomescape.domain.ReservationTime;
import roomescape.dto.TimeResponse;

public class TimeMapper {

    public static TimeResponse toResponse(ReservationTime reservationTime) {
        return new TimeResponse(
                reservationTime.id(),
                reservationTime.startAt()
        );
    }
}
