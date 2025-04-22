package roomescape.dto.request;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(
        LocalTime startAt
) {
    public ReservationTimeCreateRequest {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시간은 필수값입니다.");
        }
    }
}
