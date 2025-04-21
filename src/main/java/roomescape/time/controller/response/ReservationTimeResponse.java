package roomescape.time.controller.response;

import java.util.List;
import roomescape.time.domain.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {

    public static List<ReservationTimeResponse> from(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt().toString());
    }
}
