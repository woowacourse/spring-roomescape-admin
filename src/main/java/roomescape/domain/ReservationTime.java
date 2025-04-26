package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private long id;
    private final LocalTime startAt;

    public ReservationTime(final long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(final long id, final ReservationTime reservationTime) {
        this.id = id;
        this.startAt = reservationTime.getStartAt();
    }

    public long getId() {
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
        final ReservationTime that = (ReservationTime) o;
        return id == that.id && Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }
}
