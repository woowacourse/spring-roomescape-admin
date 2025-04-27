package roomescape.usecase.reservationTime;

import java.time.LocalTime;

public record ReservationTimeOutput(Long id, LocalTime startAt) {
}
