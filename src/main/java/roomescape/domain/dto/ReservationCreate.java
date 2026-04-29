package roomescape.domain.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationCreate(
        String name,
        LocalDate date,
        ReservationTime time
) {
}
