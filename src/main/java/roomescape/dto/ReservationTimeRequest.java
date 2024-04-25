package roomescape.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationTimeRequest(
        LocalTime startAt
) {
}
