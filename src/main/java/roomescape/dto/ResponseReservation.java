package roomescape.dto;

import roomescape.domain.Reservation;

public record ResponseReservation(Long id, String name, String date, String time) {

    public ResponseReservation(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
