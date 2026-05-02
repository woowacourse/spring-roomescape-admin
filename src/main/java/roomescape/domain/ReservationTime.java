package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(Long id, LocalTime startAt) {
    public ReservationTime {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("[ERROR] 시간 ID는 양수여야 합니다.");
        }

        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 null일 수 없습니다.");
        }
    }
}
