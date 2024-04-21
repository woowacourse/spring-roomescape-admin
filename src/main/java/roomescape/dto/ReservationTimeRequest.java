package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime from() {
        return new ReservationTime(startAt);
    }
}
