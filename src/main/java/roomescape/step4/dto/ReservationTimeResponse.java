package roomescape.step4.dto;

import roomescape.step4.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {
    public static ReservationTimeResponse from(ReservationTime newReservation) {
        return new ReservationTimeResponse(newReservation.getId(), newReservation.getStartAt());
    }
}
