package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private long id;
    private LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this(0, startAt);
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

    public void setId(long id) {
        this.id = id;
    }
}
