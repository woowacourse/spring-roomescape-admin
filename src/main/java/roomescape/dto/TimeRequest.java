package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeRequest(LocalTime startAt) {

    public Time toTime() {
        return new Time(null, startAt);
    }
}
