package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationData(
        String name,
        LocalDate date,
        LocalTime time
) {
}
