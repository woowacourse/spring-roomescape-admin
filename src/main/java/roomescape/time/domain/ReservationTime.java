package roomescape.time.domain;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validateStartAt(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this(null, startAt);
    }

    public ReservationTime(Long id, ReservationTime reservationTime) {
        this(id, reservationTime.startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시간은 null일 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationTime that)) {
            return false;
        }
        return Objects.equals(id, that.id) || Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }
}
