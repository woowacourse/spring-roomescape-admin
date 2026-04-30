package roomescape.time.repository.entity;

import java.time.LocalTime;

public class ReservationTimeEntity {

    private final LocalTime startAt;
    private Long id;

    public ReservationTimeEntity(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTimeEntity(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
