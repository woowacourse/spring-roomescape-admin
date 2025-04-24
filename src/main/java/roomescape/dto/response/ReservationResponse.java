package roomescape.dto.response;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        TimeResponse time
) {
}
