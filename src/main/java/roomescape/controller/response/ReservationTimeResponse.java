package roomescape.controller.response;

import roomescape.domain.reservation.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public record ReservationTimeResponse(Long id, LocalTime startAt) {
    public static ReservationTimeResponse from(ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getTime());
    }

    public static List<ReservationTimeResponse> listOf(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }
}
