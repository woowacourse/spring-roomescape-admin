package roomescape.dto;

import java.time.LocalTime;
import java.util.List;
import roomescape.domain.ReservationTime;

public record TimeResponse(Long id, LocalTime startAt) {

    public static TimeResponse from(final ReservationTime time) {
        return new TimeResponse(time.id(), time.time());
    }

    public static List<TimeResponse> from(final List<ReservationTime> times) {
          return times.stream()
                  .map(TimeResponse::from)
                  .toList();
    }
}
