package roomescape.business.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Time {

    private Long id;

    private final LocalTime startAt;

    public Time(final LocalTime startAt) {
        validateNonNull(startAt);
        this.startAt = startAt;
    }

    public Time(final Long id, final LocalTime startAt) {
        validateNonNull(id, startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateNonNull(final LocalTime startAt) {
        Objects.requireNonNull(startAt);
    }

    private void validateNonNull(final Long id, final LocalTime startAt) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
