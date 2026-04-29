package roomescape.domain.reservations.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservations.entity.ReservationTime;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
}
