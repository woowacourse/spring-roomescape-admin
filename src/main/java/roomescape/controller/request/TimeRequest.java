package roomescape.controller.request;

import java.time.LocalTime;
import roomescape.Time;

public record TimeRequest(LocalTime startAt) {

    public Time toTime() {
        return Time.of(this.startAt);
    }

}
