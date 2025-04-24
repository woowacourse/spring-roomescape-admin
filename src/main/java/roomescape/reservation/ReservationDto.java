package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(LocalDate date, String name, LocalTime time) {
}
