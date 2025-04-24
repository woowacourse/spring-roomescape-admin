package roomescape.reservation.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    @JsonCreator
    public ReservationTime(LocalTime startAt) {
        this.id = null;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
