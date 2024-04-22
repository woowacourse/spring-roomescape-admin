package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(long id, String time) {
    public ReservationTimeResponse(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getTime());
    }
}
