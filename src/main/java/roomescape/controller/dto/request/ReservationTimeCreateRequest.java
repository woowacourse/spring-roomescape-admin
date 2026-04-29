package roomescape.controller.dto.request;

import roomescape.domain.dto.ReservationTimeCreate;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(
        LocalTime startAt
) {

    public ReservationTimeCreate toData() {
        return new ReservationTimeCreate(startAt);
    }
}
