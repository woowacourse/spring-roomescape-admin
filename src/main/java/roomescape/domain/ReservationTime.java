package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private Long id;
    private final LocalTime startAt;

    public ReservationTime(final LocalTime startAt) {
        validateNotNull(startAt);
        this.startAt = startAt;
    }

    public ReservationTime(final Long id, final LocalTime startAt) {
        validateNotNull(id);
        validateNotNull(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateNotNull(final Object value) {
        if (value == null) {
            throw new IllegalArgumentException("값은 null이 될 수 없습니다.");
        }
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
