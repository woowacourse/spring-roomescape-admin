package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
}
