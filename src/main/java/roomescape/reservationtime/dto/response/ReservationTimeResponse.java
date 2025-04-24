package roomescape.reservationtime.dto.response;

import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {
    public static ReservationTimeResponse from(final long newId, final ReservationTime newReservationTime) {
        return new ReservationTimeResponse(newId, newReservationTime.getStartAt());
    }
}