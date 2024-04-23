package roomescape.time.dto;

import java.time.LocalTime;

public class TimeSaveRequest {
    private LocalTime startAt;

    public TimeSaveRequest() {
    }

    public TimeSaveRequest(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
