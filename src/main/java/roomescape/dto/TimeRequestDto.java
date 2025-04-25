package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain_entity.Time;

public record TimeRequestDto(LocalTime startAt) {
    public Time toTime() {
        return new Time(startAt);
    }
}
