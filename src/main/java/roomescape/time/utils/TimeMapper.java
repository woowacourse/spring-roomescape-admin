package roomescape.time.utils;

import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

@Component
public class TimeMapper {

    public Time toTime(TimeRequest timeRequest) {
        return new Time(
                timeRequest.startAt()
        );
    }

    public TimeResponse toTimeResponse(Time time) {
        return new TimeResponse(
                time.getId(),
                time.getStartAt()
        );
    }
}
