package roomescape.dto;

import java.time.LocalTime;
import java.util.List;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {

    public static ReservationTimeResponse from(final ReservationTime time) {
        return new ReservationTimeResponse(time.id(), time.time());
    }

    public static List<ReservationTimeResponse> from(final List<ReservationTime> times) {
          return times.stream()
                  .map(ReservationTimeResponse::from)
                  .toList();
    }
}
