package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
