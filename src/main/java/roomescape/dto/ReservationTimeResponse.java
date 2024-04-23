package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(long id, String startAt) {
    public ReservationTimeResponse(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }
}
