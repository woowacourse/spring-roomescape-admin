package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private LocalTime startTime;

    protected ReservationTime() {}

    public ReservationTime(LocalTime startTime) {
        this.id = null;
        this.startTime = startTime;
    }

    public ReservationTime(Long id, LocalTime startTime) {
        this.id = id;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
