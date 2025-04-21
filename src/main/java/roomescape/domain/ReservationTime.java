package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private final LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public ReservationTime(LocalTime time) {
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
