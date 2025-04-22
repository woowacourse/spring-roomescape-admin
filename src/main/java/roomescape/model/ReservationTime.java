package roomescape.model;

import java.time.LocalTime;

public final class ReservationTime {

    private final Long id;
    private final LocalTime startTime;

    public ReservationTime(Long id, LocalTime startTime) {
        this.id = id;
        this.startTime = startTime;
    }

    public ReservationTime(LocalTime startTime) {
        this.id = null;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
