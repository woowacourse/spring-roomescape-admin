package roomescape.dto.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.time.Time;

public class TimeResponse {

    private final Long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    private TimeResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeResponse from(Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
