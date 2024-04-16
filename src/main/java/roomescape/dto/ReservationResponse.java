package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
        String name,
        String date,
        String time) {

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
