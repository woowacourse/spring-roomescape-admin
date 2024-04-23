package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private static final Long DEFAULT_ID_VALUE = 0L;

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final LocalTime startAt) {
        this(DEFAULT_ID_VALUE, startAt);
    }

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime initializeIndex(final Long reservationId) {
        return new ReservationTime(reservationId, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
