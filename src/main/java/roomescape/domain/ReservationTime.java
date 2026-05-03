package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(long id, LocalTime startAt) {

    public ReservationTime {
        validate(startAt);
    }

    private void validate(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간은 필수입니다.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("유효하지 않은 예약 시간대 번호입니다.");
        }
    }
}
