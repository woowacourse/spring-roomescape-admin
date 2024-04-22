package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.time.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toTime() {
        return new ReservationTime(startAt);
    }
}
