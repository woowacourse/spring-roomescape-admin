package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private Long id;
    private final LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(Long id, ReservationTime time) {
        this.id = id;
        this.startAt = time.startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
