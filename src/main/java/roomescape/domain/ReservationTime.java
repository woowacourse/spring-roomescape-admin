package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setId(long id) {
        this.id = id;
    }
}
