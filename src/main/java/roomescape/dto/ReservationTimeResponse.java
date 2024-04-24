package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        String startAt
) {

    public ReservationTime toEntity() {
        return new ReservationTime(
                id,
                LocalTime.parse(startAt)
        );
    }
}
