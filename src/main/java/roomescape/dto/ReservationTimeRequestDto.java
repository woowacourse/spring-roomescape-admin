package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequestDto(
        LocalTime startAt
) {
    public ReservationTime toEntity(Long id) {
        return ReservationTime.builder()
                .id(id)
                .startAt(this.startAt)
                .build();
    }
}
