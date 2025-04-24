package roomescape.dto;

import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record ReservationTimeRequestDto(LocalTime startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
