package roomescape.dto.response;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ResponseReservation(Long id, String name, String date, ReservationTime time) {

    public ResponseReservation(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getReservationTime());
    }
}
