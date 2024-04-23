package roomescape.dto.reservationtime;

import java.time.LocalTime;
import java.util.Objects;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTimeRequest {
        Objects.requireNonNull(startAt, "시작 시간은 필수입니다.");
    }
}
