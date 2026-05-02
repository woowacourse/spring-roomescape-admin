package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    public static final String TIME_SHOULD_NOT_BE_NULL = "시간을 입력해야 합니다.";
    private final long id;
    private final LocalTime startAt;

    private ReservationTime(long id, String startAt) {
        this.id = id;
        this.startAt = LocalTime.parse(startAt);
    }

    public static ReservationTime of(long id, String startAt) {
        validateIsNull(startAt);
        return new ReservationTime(id, startAt);
    }

    private static void validateIsNull(String startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException(TIME_SHOULD_NOT_BE_NULL);
        }
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
