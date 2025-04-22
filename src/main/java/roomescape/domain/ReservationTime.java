package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private long id;
    private final LocalTime startAt;

    private ReservationTime(final long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity(long id) {
        return new ReservationTime(id, this.startAt);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
