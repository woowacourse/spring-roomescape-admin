package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
