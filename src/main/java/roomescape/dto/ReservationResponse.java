package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
}
