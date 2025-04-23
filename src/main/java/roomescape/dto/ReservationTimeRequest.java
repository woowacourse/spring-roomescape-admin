package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {

    public ReservationTime toReservationTime(Long id) {
        return new ReservationTime(
                id,
                startAt
        );
    }
}
