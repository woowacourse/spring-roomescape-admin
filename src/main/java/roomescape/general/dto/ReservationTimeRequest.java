package roomescape.general.dto;

import java.time.LocalTime;
import java.util.Objects;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTimeRequest {
        Objects.requireNonNull(startAt);
    }
}
