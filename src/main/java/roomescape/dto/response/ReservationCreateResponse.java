package roomescape.dto.response;

import java.time.LocalDate;

public record ReservationCreateResponse(long id, String name, LocalDate date, ReservationTimeResponse time) {
}
