package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequestDto(
        LocalTime startAt
) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(null, this.startAt);
    }
}
