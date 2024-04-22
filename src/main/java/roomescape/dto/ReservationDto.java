package roomescape.dto;

import java.time.LocalDate;

public record ReservationDto(String name, LocalDate date, Long timeId) {
}
