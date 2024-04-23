package roomescape.data.vo;

import java.time.LocalTime;

public class ReservationTime {
    private static final long UNDEFINED_TABLE_ID = 0L;

    private final long id;
    private final LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this.id = UNDEFINED_TABLE_ID;
        this.startAt = startAt;
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
}
