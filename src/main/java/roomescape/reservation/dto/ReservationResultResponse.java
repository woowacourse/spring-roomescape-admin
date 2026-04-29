package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResultResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
}
