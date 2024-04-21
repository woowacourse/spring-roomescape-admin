package roomescape.dto;

import java.time.LocalTime;

public record TimeSaveRequest(
        long id,
        LocalTime startAt
) {
    public TimeSaveRequest(LocalTime startAt) {
        this(0L, startAt);
    }
}
