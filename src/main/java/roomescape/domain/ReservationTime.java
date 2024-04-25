package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;
import org.springframework.cglib.core.Local;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        Objects.requireNonNull(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime from(final LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
