package roomescape.business.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Time {

    private Long id;

    private final LocalTime startAt;

    public Time(final LocalTime startAt) {
        this(null, startAt);
    }

    public static Time createWithId(final Long id, final LocalTime startAt) {
        Objects.requireNonNull(id, "id가 null 입니다.");
        return new Time(id, startAt);
    }

    private Time(final Long id, final LocalTime startAt) {
        validateNonNull(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateNonNull(final LocalTime startAt) {
        Objects.requireNonNull(startAt, "startAt이 null 입니다.");
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
        final Time time = (Time) o;
        return Objects.equals(id, time.id) && Objects.equals(startAt, time.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }
}
