package roomescape.dto.request;

import java.time.LocalTime;

public record ReservationTimeRequest(

        LocalTime startAt
) {
    public ReservationTimeRequest {
        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 시작 시간은 필수입니다.");
        }
    }
}
