package roomescape.domain.reservations.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
}
