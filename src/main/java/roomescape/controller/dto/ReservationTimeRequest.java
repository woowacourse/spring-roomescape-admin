package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(String startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }
}
