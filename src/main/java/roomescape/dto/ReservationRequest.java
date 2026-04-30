package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(String date, String name, Long timeId) {

    public Reservation toEntity(ReservationTime reservationTime, Long id) {
        return new Reservation(id, this.name, this.date, reservationTime.getId(), reservationTime.getStartAt());
    }
}
