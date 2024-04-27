package roomescape.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class ReservationTime {

    private final long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTime(long id, String startAt) {
        this.id = id;
        this.startAt = LocalTime.parse(startAt);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
