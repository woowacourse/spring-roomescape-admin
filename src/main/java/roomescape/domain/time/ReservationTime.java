package roomescape.domain.time;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {

    private final LocalTime startAt;
    private Long id;

    public ReservationTime(
        final Long id,
        final LocalTime startAt
    ) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(
        final LocalTime startAt
    ) {
        this(null, startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationTime reservationTime)) {
            return false;
        }
        return Objects.equals(startAt, reservationTime.startAt) && Objects.equals(id, reservationTime.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt, id);
    }

    @Override
    public String toString() {
        return "Time{" +
            "startAt=" + startAt +
            ", id=" + id +
            '}';
    }
}
