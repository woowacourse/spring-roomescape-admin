package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(String name, String date, Long timeId) {

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(0L, name, date, reservationTime);
    }
}
