package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequestDto(
        LocalTime startAt
) {
    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startAt(this.startAt)
                .build();
    }
}
