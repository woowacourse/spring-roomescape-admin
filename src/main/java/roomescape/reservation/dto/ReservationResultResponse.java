package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.time.dto.ReservationTimeResponse;

public record ReservationResultResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
}
