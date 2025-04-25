package roomescape.model;

import java.time.LocalTime;

public final class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime from(final Long id, final LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    public static ReservationTime ofWithoutId(final LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
