package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(22, 0);

    private final LocalTime time;

    public ReservationTime(LocalTime time) {
        validateTime(time);
        this.time = time;
    }

    public static LocalTime startTime() {
        return START_TIME;
    }

    public static LocalTime endTime() {
        return END_TIME;
    }

    private void validateTime(LocalTime time) {
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException("예약 가능 시간은 " + START_TIME + "부터 " + END_TIME + "까지입니다.");
        }
    }
}
