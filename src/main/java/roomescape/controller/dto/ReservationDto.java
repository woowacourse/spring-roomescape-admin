package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(String name, LocalDate date, LocalTime time) {
}
