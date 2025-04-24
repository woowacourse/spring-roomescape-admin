package roomescape.time.utils;

import org.springframework.stereotype.Component;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;

@Component
public class ReservationTimeMapper {

    public ReservationTime toTime(ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(
                reservationTimeRequest.startAt()
        );
    }

    public ReservationTimeResponse toTimeResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}
