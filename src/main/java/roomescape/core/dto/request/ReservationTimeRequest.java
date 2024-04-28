package roomescape.core.dto.request;

import java.time.LocalTime;
import roomescape.core.domain.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(null, startAt);
    }
}
