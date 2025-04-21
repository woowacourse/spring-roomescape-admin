package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(
        long id,
        LocalTime startTime
) {
    public ReservationTime {
        if (startTime == null) {
            throw new IllegalArgumentException("시작 시간은 null이 될 수 없습니다.");
        }
    }
}
