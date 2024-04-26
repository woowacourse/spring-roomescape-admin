package roomescape.domain;

import java.sql.Time;
import java.time.LocalTime;

public class ReservationTime {
    private final long id;
    private final LocalTime startAt;

    public ReservationTime(long id, String startAt) {
        this(id, LocalTime.parse(startAt));
    }

    public ReservationTime(long id, Time startAt) {
        this(id, startAt.toLocalTime());
    }

    public ReservationTime(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
