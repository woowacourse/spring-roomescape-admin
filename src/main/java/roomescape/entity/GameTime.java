package roomescape.entity;

import java.time.LocalTime;
import java.util.Objects;

public class GameTime {
    private final Long id;
    private final LocalTime startAt;

    public GameTime(Long id, LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public GameTime(LocalTime startAt) {
        this(null, startAt);
    }

    private void validate(LocalTime startAt) {
        validateNonNull(startAt);
    }

    private void validateNonNull(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("예약 가능한 시간은 null일 수 없습니다");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameTime that = (GameTime) o;
        return Objects.equals(id, that.id) && Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }
}
