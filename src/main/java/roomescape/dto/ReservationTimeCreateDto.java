package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeCreateDto(LocalTime startAt) {

    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startAt(startAt)
                .build();
    }
}
