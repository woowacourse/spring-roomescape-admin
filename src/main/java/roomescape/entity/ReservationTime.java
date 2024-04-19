package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id; // TODO: long vs Long 고민
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
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
