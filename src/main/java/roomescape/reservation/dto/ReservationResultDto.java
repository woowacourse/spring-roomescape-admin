package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResultDto(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
}
