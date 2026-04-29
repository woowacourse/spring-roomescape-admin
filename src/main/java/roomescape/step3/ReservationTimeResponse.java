package roomescape.step3;

import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {
    public static ReservationTimeResponse from(ReservationTime newReservation) {
        return new ReservationTimeResponse(newReservation.getId(), newReservation.getStartAt());
    }
}
