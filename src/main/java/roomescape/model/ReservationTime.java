package roomescape.model;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(final String startAt) {
        return new ReservationTime(null, LocalTime.parse(startAt));
    }

    public ReservationTime toReservationTime(final long id) {
        return new ReservationTime(id, startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReservationTime that = (ReservationTime) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getStartAt(), that.getStartAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartAt());
    }
}
