package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime start_at;

    public ReservationTime(final Long id, final LocalTime start_at) {
        validateNotNull(start_at);
        this.id = id;
        this.start_at = start_at;
    }

    private void validateNotNull(final LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간이 존재해야 합니다.");
        }
    }

    public LocalTime getStart_at() {
        return start_at;
    }

    public Long getId() {
        return id;
    }
}
