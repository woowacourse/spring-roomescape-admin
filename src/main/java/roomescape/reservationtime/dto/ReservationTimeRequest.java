package roomescape.reservationtime.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeRequest (
        @NotNull(message = "시간은 필수입니다")
        LocalTime startAt
) {
    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startAt(startAt)
                .build();
    }
}
