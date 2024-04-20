package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(LocalDate date, String name, LocalTime time) {
}
