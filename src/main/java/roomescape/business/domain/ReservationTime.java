package roomescape.business.domain;

import java.time.LocalTime;

public class ReservationTime {

    private static final LocalTime MIN_TIME = LocalTime.of(9, 0);
    private static final LocalTime MAX_TIME = LocalTime.of(23, 0);

    private final LocalTime startTime;

    public ReservationTime(final LocalTime startTime) {
        validateStartTime(startTime);
        this.startTime = startTime;
    }

    private static void validateStartTime(final LocalTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("시작 시간은 null이 될 수 없습니다.");
        }
        if (startTime.isBefore(MIN_TIME)) {
            throw new IllegalArgumentException("시작 시간은 9시 이후이어야 합니다.");
        }
        if (startTime.isAfter(MAX_TIME)) {
            throw new IllegalArgumentException("시작 시간은 23시 이전이어야 합니다.");
        }
    }

    public LocalTime startTime() {
        return startTime;
    }
}
