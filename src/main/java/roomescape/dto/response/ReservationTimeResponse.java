package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public record ReservationTimeResponse(Long id, @JsonFormat(pattern = "HH:mm") LocalTime startAt) {

    public static ReservationTimeResponse of(final ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getStartAt());
    }

    public static List<ReservationTimeResponse> from(final List<ReservationTime> times) {
        return times.stream()
                .map(reservation -> new ReservationTimeResponse(reservation.getId(), reservation.getStartAt()))
                .toList();
    }
}
