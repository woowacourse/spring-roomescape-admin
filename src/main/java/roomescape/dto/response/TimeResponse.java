package roomescape.dto.response;

import roomescape.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public record TimeResponse(
        Long id,
        LocalTime startAt
) {

    public static TimeResponse from(ReservationTime time) {
        return new TimeResponse(
                time.getId(),
                time.getStartAt()
        );
    }

    public static List<TimeResponse> from(List<ReservationTime> times) {
        return times.stream()
                .map(TimeResponse::from)
                .toList();
    }
}
