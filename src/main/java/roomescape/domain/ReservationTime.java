package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private final long id;
    private final LocalTime startAt;

    private ReservationTime(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(long id, LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    public LocalTime startAt() {
        return startAt;
    }

    public long id() {
        return id;
    }
}
