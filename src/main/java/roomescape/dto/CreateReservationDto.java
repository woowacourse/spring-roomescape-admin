package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationDto(String name, LocalDate date, LocalTime time) {
}
