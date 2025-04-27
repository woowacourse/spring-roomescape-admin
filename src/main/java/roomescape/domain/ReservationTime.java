package roomescape.domain;

import java.time.LocalTime;
import roomescape.domain.exception.EmptyReservationTimeException;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        validateStartTime(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final LocalTime startAt) {
        this(null, startAt);
    }

    private void validateStartTime(final LocalTime startAt) {
        if (startAt == null) {
            throw new EmptyReservationTimeException("예약 시작 시간은 null이 될 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
