package roomescape.dto;

import java.util.List;
import roomescape.model.ReservationTime;

public record ReservationTimeResponse(long id, String startAt) {

    private static ReservationTimeResponse toResponse(final ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt().toString()
        );
    }

    public static List<ReservationTimeResponse> toResponses(final List<ReservationTime> reservationTimes) {
        return reservationTimes.stream().map(ReservationTimeResponse::toResponse).toList();
    }
}
