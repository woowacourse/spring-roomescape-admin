package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final long id;
    private final LocalTime time;

    public ReservationTime(long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public long getId() {
        return id;
    }
}
