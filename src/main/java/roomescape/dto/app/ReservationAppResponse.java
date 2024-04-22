package roomescape.dto.app;

import java.time.LocalTime;

public record ReservationAppResponse(Long id, LocalTime startAt) {
}
