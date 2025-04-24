package roomescape.reservationTime.dto;

import java.util.List;
import roomescape.reservationTime.ReservationTime;

public record AllReservationTimeResponse(List<ReservationTimeResponse> reservationTimes) {
    public static AllReservationTimeResponse from(List<ReservationTime> reservationTimes) {
        return new AllReservationTimeResponse(
                reservationTimes.stream()
                        .map(ReservationTimeResponse::from)
                        .toList()
        );
    }
}
