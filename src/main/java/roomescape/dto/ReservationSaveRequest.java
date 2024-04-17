package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationSaveRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
}
