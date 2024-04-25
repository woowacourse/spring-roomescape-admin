package roomescape.dto;

import roomescape.domain.reservation.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public record ReservationTimeResponse(Long id, LocalTime startAt) {
    public ReservationTimeResponse(ReservationTime time) {
        this(time.getId(), time.getTime());
    }

    public static List<ReservationTimeResponse> listOf(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }
}
