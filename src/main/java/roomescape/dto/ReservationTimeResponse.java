package roomescape.dto;

import roomescape.entity.ReservationTime;

public record ReservationTimeResponse(long id, String startAt) {

    public ReservationTimeResponse(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }
}
