package roomescape.controller.dto.request;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeCreateRequest(LocalTime startAt){

    public Time toTime() {
        return new Time(null, startAt);
    }
}
