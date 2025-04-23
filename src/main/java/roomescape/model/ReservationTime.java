package roomescape.model;

import java.time.LocalTime;
import roomescape.exception.DomainException;

public final class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        validateNotNullTime(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateNotNullTime(LocalTime time) {
        if (time == null) {
            throw new DomainException("예약 시간이 입력되지 않았습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
