package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;

public final class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this(null, startAt);
    }

    public ReservationTime withId(long id) {
        return new ReservationTime(id, startAt);
    }

    public Long id() {
        return id;
    }

    public LocalTime startAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        ReservationTime other = (ReservationTime) obj;
        return this.id != null
            && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
