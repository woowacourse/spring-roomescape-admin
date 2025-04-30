package roomescape.controller.reservation.request;

import java.time.LocalDate;
import roomescape.service.reservation.request.ReservationServiceRequest;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId) {

    public ReservationServiceRequest toServiceRequest() {
        return new ReservationServiceRequest(this.name, this.date, this.timeId);
    }
}
