package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.service.dto.ReservationTimeServiceRequest;

public record ReservationTimeRequest(
        LocalTime startAt
) {

    public ReservationTimeServiceRequest toReservationTimeServiceRequest() {
        return new ReservationTimeServiceRequest(startAt);
    }
}
