package roomescape.presentation.dto.request;

import java.time.LocalTime;
import java.util.Objects;

public record ReservationTimeCreateRequest(
        LocalTime startAt
) {
    public ReservationTimeCreateRequest {
        Objects.requireNonNull(startAt, "시작 시간은 필수값입니다.");
    }
}
