package roomescape.reservationtime.web.dto;

import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeCreateDto(LocalTime startAt) {

    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startAt(startAt)
                .build();
    }
}
