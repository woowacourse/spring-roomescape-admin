package roomescape.dto.request;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(LocalTime startAt) {

    public ReservationTimeCreateRequest {
        validateBlank(startAt);
    }

    private void validateBlank(final LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("빈 값으로 예약할 수 없습니다.");
        }
    }
}
