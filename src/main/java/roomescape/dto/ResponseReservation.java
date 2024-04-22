package roomescape.dto;

import roomescape.domain.Reservation;

public record ResponseReservation(Long id, String name, String date, ResponseTimes time) {

    public ResponseReservation(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), new ResponseTimes(reservation.getReservationTime()));
    }
}
