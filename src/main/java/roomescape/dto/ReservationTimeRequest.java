package roomescape.dto;

import java.time.LocalTime;

public record ReservationTimeRequest(Long id, LocalTime startAt) {
}
