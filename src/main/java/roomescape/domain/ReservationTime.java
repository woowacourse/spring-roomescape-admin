package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private final long id;
    private final LocalTime time;

    public ReservationTime(final long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
