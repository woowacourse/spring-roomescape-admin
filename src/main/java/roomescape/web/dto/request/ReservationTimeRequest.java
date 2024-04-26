package roomescape.web.dto.request;

import java.time.LocalTime;
import roomescape.web.domain.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(null, startAt);
    }
}
