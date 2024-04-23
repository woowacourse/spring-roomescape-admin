package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(
        String startAt) {

    public ReservationTime toDomain() {
        return new ReservationTime(startAt());
    }

}
