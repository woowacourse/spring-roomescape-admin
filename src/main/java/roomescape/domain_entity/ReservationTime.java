package roomescape.domain_entity;

import java.time.LocalTime;

public class ReservationTime {
    private Id id;
    private LocalTime startAt;

    public ReservationTime() {
    }

    public ReservationTime(Id id) {
        this.id = id;
    }

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(Id id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public long getId() {
        return id.value();
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
