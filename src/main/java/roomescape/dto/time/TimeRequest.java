package roomescape.dto.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import roomescape.domain.time.Time;

public class TimeRequest {

    private final LocalTime startAt;

    @JsonCreator
    public TimeRequest(@JsonProperty("startAt") LocalTime startAt) {
        this.startAt = startAt;
    }

    public Time toTime() {
        return new Time(this.startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
