package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeResponse(long id, LocalTime startAt) {

    public TimeResponse(long id, TimeRequest timeRequest) {
        this(id, timeRequest.startAt());
    }

    public TimeResponse(Time time) {
        this(time.getId(), time.getStartAt());
    }
}
