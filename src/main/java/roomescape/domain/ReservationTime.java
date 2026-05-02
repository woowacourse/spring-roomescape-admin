package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final long id;
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
