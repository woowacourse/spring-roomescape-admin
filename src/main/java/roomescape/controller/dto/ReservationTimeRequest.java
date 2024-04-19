package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(String startAt) {

    public ReservationTime toInstance() {
        return new ReservationTime(startAt);
    }
}
