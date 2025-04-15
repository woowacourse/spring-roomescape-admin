package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateDto(String name, LocalDate date, LocalTime time) {
}
