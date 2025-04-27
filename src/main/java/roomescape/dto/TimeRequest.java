package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeRequest(LocalTime startAt) {

    public Time toEntity() {
        return new Time(startAt);
    }
}
