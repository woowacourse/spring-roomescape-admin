package roomescape;

import java.time.LocalTime;

public class ReservationTime {

    Long id;
    LocalTime startTime;

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
