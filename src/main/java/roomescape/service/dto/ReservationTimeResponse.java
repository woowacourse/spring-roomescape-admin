package roomescape.service.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(Long id, String time) {

    public static ReservationTimeResponse of(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getTime());
    }
}
