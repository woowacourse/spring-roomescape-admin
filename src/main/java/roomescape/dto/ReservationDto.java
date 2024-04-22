package roomescape.dto;

import java.time.LocalDate;

public record ReservationDto(long id, String name, LocalDate date, long timeId) {
}
