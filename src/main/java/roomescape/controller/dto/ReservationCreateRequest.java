package roomescape.controller.dto;

import roomescape.domain.Reservation;

public record ReservationCreateRequest(String name, String date, String time) {

    public Reservation toReservation(final Long id) {
        return new Reservation(id, name, date, time);
    }
}
