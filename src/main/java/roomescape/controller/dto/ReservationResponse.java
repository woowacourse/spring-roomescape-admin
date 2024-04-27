package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.Time;

public record ReservationResponse(long id, String name, LocalDate date, Time time) {
}
