package roomescape.service;

import org.springframework.stereotype.Component;
import roomescape.controller.time.TimeRequest;
import roomescape.controller.time.TimeResponse;
import roomescape.entity.ReservationTime;

import java.time.format.DateTimeFormatter;

@Component
public class ReservationTimeMapper {

    public ReservationTime map(TimeRequest request) {
        return new ReservationTime(null, request.startAt());
    }

    public TimeResponse map(ReservationTime time) {
        return new TimeResponse(
                time.id(),
                time.startAt().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
