package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validateStartAt(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간이 null일 수 없습니다.");
        }
    }

    public ReservationTime withId(Long id) {
        return new ReservationTime(id, startAt);
    }

    public boolean isAfter(LocalTime comparedTime) {
        return startAt.isAfter(comparedTime);
    }

    public boolean isEqualId(Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
