package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validate(final LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간이 존재하지 않습니다.");
        }
    }
}
