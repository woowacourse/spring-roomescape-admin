package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {

    Long id;
    LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
