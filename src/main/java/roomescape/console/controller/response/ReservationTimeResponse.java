package roomescape.console.controller.response;

import roomescape.core.service.response.ReservationTimeResponseDto;

import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {
    public static ReservationTimeResponse from(ReservationTimeResponseDto reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.id(),
                reservationTime.startAt());
    }
}
