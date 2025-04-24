package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeRequest(LocalTime startAt) {

    public Time toDomain() {
        return new Time(startAt);
    }
}
