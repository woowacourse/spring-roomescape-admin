package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.service.dto.ReservationServiceRequest;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {

        public ReservationServiceRequest toReservationServiceRequest() {
                return new ReservationServiceRequest(name, date, timeId);
        }
}
