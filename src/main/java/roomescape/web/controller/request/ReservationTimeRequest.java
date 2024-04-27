package roomescape.web.controller.request;

import roomescape.core.domain.ReservationTime;

import java.time.LocalTime;
import java.util.Objects;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTimeRequest {
        Objects.requireNonNull(startAt);
    }

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }
}
