package roomescape.dto;

import java.util.List;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(long id, String startAt) {

    public static ReservationTimeResponse toResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public static List<ReservationTimeResponse> toResponses(List<ReservationTime> times) {
        return times.stream()
                .map(time -> new ReservationTimeResponse(time.getId(), time.getStartAt()))
                .toList();
    }
}
