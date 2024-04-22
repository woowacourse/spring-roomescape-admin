package roomescape.dto.reservationtime;

import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {
}
