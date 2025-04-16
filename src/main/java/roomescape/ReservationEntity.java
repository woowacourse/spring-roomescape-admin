package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationEntity(Long id, String name, LocalDate date, LocalTime time) {
}
