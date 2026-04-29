package roomescape.reservationtime.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeCreateRequest(
        @NotNull(message = "[ERROR] 시간은 비어있을 수 없습니다.")
        LocalTime startAt
) {

    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startAt(startAt)
                .build();
    }
}
