package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeDto(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {
    public ReservationTimeDto(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }
}
