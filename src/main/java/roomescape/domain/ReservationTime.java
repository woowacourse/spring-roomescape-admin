package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final long id;
    private final LocalTime startAt;

    public ReservationTime(final long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(final long id, final String startAt) {
        return new ReservationTime(id, LocalTime.parse(startAt));
    }

    public static ReservationTime of(final LocalTime startAt) {
        return new ReservationTime(0L, startAt);
    }

    public boolean isBefore(final LocalTime time) {
        return startAt.isBefore(time);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
