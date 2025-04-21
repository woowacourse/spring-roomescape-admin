package roomescape.time.controller.request;

import java.time.LocalTime;
import roomescape.time.domain.Time;

public record TimeCreateRequest(String startAt) {

    public Time to() {
        return new Time(LocalTime.parse(startAt));
    }
}
