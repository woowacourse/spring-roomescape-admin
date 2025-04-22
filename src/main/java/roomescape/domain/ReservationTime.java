package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(Long id, ReservationTime reservationTime) {
        this(id, reservationTime.startAt);
    }

    public boolean isAfter(LocalTime comparedTime) {
        return startAt.isAfter(comparedTime);
    }

    public boolean isEqualId(Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
