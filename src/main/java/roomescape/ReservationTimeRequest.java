package roomescape;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record ReservationTimeRequest(
        @NotNull LocalTime startAt
) {
    public ReservationTime toEntity() {
        return new ReservationTime(
                null,
                startAt
        );
    }
}
