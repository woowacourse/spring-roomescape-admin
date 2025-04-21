package roomescape.time.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.time.domain.Time;

public record TimeRequest(
        @NotNull LocalTime startAt
) {
    public Time toTime() {
        return new Time(null, startAt);
    }
}
