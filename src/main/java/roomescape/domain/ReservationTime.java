package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime createNew(LocalTime startAt) {
        validateStartAt(startAt);
        return new ReservationTime(null, startAt);
    }

    public static ReservationTime from(Long id, LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    private static void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간은 필수입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
