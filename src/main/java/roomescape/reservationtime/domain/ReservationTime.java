package roomescape.reservationtime.domain;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final Long id, final String startAt) {
        this.id = id;
        this.startAt = LocalTime.parse(startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getStartAt(), that.getStartAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartAt());
    }
}
