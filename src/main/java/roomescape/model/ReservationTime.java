package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(final String startAt) {
        return new ReservationTime(null, LocalTime.parse(startAt));
    }

    public ReservationTime toReservationTime(final long id) {
        return new ReservationTime(id, startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public Long getId() {
        return id;
    }
}
