package roomescape.time;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {

    private final long id;
    private final LocalTime startAt;

    public Time(final long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    @JsonProperty("start_at")
    @JsonFormat(pattern = "HH:mm")
    public LocalTime getStartAt() {
        return startAt;
    }
}
