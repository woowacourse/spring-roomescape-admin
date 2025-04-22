package roomescape.entity;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(final Long id, final LocalTime startAt) {
        validateTime(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(final LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public static ReservationTime of(final Long id, final LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    private void validateTime(final LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 예약시간은 반드시 입력해야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
