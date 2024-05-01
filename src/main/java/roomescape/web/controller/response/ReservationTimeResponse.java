package roomescape.web.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.core.service.response.ReservationTimeResponseDto;

import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {
    public static ReservationTimeResponse from(ReservationTimeResponseDto reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.id(),
                reservationTime.startAt());
    }
}
