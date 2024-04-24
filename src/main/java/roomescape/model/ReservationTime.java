package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {
    public static final Long NULL_ID = null;
    private static final LocalTime NULL_START_AT = null;
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(Long id) {
        this.id = id;
        this.startAt = NULL_START_AT;
    }

    public ReservationTime(LocalTime startAt) {
        this.id = NULL_ID;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
