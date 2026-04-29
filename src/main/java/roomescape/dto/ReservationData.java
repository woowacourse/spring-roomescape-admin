package roomescape.dto;

import roomescape.ReservationTime;

import java.time.LocalDate;

public record ReservationData(
        String name,
        LocalDate date,
        ReservationTime time
) {
}
