package roomescape.model;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {

    private final EntityId id;
    private final LocalTime startAt;

    public ReservationTime(EntityId id, LocalTime startAt) {
        validateNotNull(id, startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateNotNull(EntityId id, LocalTime startAt) {
        if (id == null) {
            throw new IllegalArgumentException("id를 올바르게 입력해 주세요.");
        }
        if (startAt == null) {
            throw new IllegalArgumentException("시간을 올바르게 입력해 주세요.");
        }
    }

    public Long getId() {
        return id.getId();
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt);
    }
}
