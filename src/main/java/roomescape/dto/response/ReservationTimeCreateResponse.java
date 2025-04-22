package roomescape.dto.response;

import java.time.LocalTime;

public record ReservationTimeCreateResponse(long id, LocalTime startAt) {
}
