package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private Long id;
    private LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public boolean isBefore(final LocalTime comparedTime) {
        return startAt.isBefore(comparedTime);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
