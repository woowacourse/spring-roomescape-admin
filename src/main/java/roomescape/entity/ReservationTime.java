package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public Long getId() {
        return id;
    }
}
