package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.service.dto.ReservationTimeServiceResponse;

public record ReservationTimeResponse(
        Long id,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startAt
) {

        public static ReservationTimeResponse from(ReservationTimeServiceResponse reservationTimeServiceResponse) {
                return new ReservationTimeResponse(reservationTimeServiceResponse.id(), reservationTimeServiceResponse.startAt());
        }
}
