package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(
        Long id,
        LocalTime startAt
) {
    public ReservationTime {
        validateStartAt(startAt);
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간은 필수입니다.");
        }
    }
}
