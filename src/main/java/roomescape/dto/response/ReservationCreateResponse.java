package roomescape.dto.response;

import java.time.LocalTime;

public record ReservationCreateResponse(long id, LocalTime startAt) {
}
