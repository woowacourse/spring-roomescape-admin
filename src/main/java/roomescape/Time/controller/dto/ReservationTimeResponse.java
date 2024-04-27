package roomescape.Time.controller.dto;

import java.time.LocalTime;

public record ReservationTimeResponse(long id, LocalTime startAt) {
}
