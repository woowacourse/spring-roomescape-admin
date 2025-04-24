package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {
    private long id;
    private LocalTime startAt;

    public ReservationTime(final long id,final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
