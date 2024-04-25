package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTimeRequest {
        Objects.requireNonNull(startAt);
    }
}
