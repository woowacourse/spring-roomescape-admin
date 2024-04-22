package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationDto(String name, LocalDate date, Long timeId) {
}
