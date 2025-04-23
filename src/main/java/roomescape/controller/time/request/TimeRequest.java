package roomescape.controller.time.request;

import java.time.LocalTime;
import roomescape.model.Time;

public record TimeRequest(LocalTime startAt) {

    public Time toTime() {
        return Time.of(this.startAt);
    }

}
