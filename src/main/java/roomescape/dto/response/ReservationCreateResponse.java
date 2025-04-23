package roomescape.dto.response;

import java.time.LocalDate;
import roomescape.domain.ReservationTime;

public record ReservationCreateResponse(long id, String name, LocalDate date, ReservationTime time) {
}
