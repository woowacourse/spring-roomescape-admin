package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {
    private long id;
    private LocalTime time;

    public ReservationTime(final long id,final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public ReservationTime(final LocalTime time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
