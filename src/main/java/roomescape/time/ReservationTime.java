package roomescape.time;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ReservationTime(long id, LocalTime startAt) {
    public ReservationTime(LocalTime startAt) {
        this(0, startAt);
    }
    @JsonFormat(pattern = "HH:mm")
    public LocalTime startAt() {
        return startAt;
    }
}
