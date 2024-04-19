package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.service.dto.ReservationServiceRequest;

public record ReservationRequest(
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,
        Long timeId
) {

        public ReservationServiceRequest toReservationServiceRequest() {
                return new ReservationServiceRequest(name, date, timeId);
        }
}
