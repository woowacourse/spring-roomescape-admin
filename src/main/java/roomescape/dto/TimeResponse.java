package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeResponse(Long id, LocalTime startAt) {

    public static TimeResponse from(final Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }

    public static TimeResponse withId(final Long id, final Time time) {
        return new TimeResponse(id, time.getStartAt());
    }
}
