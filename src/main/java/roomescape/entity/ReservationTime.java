package roomescape.entity;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this(null, startAt);
    }

    private void validate(LocalTime time) {
        validateNonNull(time);
        validateHourlyUnit(time);
    }

    private void validateNonNull(LocalTime time) {
        if (time == null) {
            throw new NullPointerException("예약 가능한 시간은 null일 수 없습니다");
        }
    }

    private void validateHourlyUnit(LocalTime time) {
        if (time.getMinute() != 0) {
            throw new IllegalStateException("예약 가능 시각은 정각 단위여야 합니다: " + time);
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
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(id, that.id) && Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }
}
