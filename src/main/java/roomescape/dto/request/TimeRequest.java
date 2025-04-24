package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeRequest(
        @NotNull LocalTime startAt
) {
    public Time toTime() {
        return new Time(startAt);
    }
}
