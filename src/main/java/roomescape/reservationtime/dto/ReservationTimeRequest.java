package roomescape.reservationtime.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeRequest(
        @NotNull LocalTime startAt
) {
    public ReservationTime toTime() {
        return new ReservationTime(null, startAt);
    }
}
