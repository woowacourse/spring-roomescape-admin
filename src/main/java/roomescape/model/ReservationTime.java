package roomescape.model;

import java.time.LocalTime;
import roomescape.exception.UserIllegalArgumentException;

public final class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(final Long id, final LocalTime startAt) {
        validateNotNullTime(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final LocalTime startAt) {
        this(null, startAt);
    }

    public static ReservationTime toEntity(Long id, LocalTime startAt) {
        validateNotNullId(id);
        return new ReservationTime(id, startAt);
    }

    private static void validateNotNullId(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id는 null일 수 없습니다.");
        }
    }

    private void validateNotNullTime(final LocalTime time) {
        if (time == null) {
            throw new UserIllegalArgumentException("예약 시간이 입력되지 않았습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
