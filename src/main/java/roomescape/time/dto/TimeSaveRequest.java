package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.time.domain.Time;

public class TimeSaveRequest {
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startAt;

    public TimeSaveRequest() {
    }

    public TimeSaveRequest(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public Time toTime() {
        return new Time(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
