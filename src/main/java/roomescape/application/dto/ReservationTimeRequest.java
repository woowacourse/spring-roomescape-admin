package roomescape.application.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {

    public ReservationTime toReservationTimeWithNullId() {
        return new ReservationTime(
                null,
                startAt
        );
    }
}
