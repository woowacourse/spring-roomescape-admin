package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;

    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime time) {
        this.id = id;
        this.startAt = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
