package roomescape.entity;

import java.time.LocalTime;

public record ReservationTimeEntity(
        Long id,
        LocalTime startAt
) {
    public ReservationTimeEntity {
        // 운영 시간 검증
    }
}
