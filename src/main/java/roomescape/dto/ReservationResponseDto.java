package roomescape.dto;

import java.time.LocalDate;
import roomescape.entity.ReservationTime;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        ReservationTime time
) {
}
