package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.List;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(long id, @JsonFormat(pattern = "HH:mm") LocalTime startAt) {

    public static List<ReservationTimeResponse> fromReservationTimes(final List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(reservationTime -> new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt()))
                .toList();
    }
}
