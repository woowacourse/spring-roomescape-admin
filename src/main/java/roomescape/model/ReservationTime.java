package roomescape.model;

import java.time.LocalTime;

public final class ReservationTime {

    private final Integer id;
    private final LocalTime startAt;

    public ReservationTime(Integer id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this.id = null;
        this.startAt = startAt;
    }

    public ReservationTime createWithId(Integer id) {
        return new ReservationTime(id, startAt);
    }

    public Integer getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
