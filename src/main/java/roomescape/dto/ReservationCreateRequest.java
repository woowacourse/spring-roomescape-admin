package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationCreateRequest(String name, String date, String time) {

    public Reservation createReservation(long id) {
        return new Reservation(id, name, date, time);
    }
}
