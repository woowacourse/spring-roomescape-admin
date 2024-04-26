package roomescape.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.core.service.dto.ReservationServiceResponse;

public record ReservationResponse(
        Long id,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,
        ReservationTimeResponse time
) {

    public static ReservationResponse from(ReservationServiceResponse reservationServiceResponse) {
        return new ReservationResponse(
                reservationServiceResponse.id(),
                reservationServiceResponse.name(),
                reservationServiceResponse.date(),
                ReservationTimeResponse.from(reservationServiceResponse.time())
        );
    }
}
