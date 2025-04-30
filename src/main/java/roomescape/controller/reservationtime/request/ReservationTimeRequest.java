package roomescape.controller.reservationtime.request;

import java.time.LocalTime;
import roomescape.service.reservationtime.request.ReservationTimeServiceRequest;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTimeServiceRequest toServiceRequest() {
        return new ReservationTimeServiceRequest(this.startAt);
    }
}
