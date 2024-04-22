package roomescape.service;

import roomescape.controller.time.TimeRequest;
import roomescape.controller.time.TimeResponse;
import roomescape.entity.ReservationTime;

import java.time.format.DateTimeFormatter;

public class ReservationTimeMapper {

    public static ReservationTime map(TimeRequest request) {
        return new ReservationTime(null, request.startAt());
    }

    public static TimeResponse map(ReservationTime time) {
        return new TimeResponse(
                time.id(),
                time.startAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
