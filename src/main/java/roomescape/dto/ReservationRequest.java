package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
}
