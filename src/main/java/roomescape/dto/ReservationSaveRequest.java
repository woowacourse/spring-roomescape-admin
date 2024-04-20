package roomescape.dto;

import roomescape.model.Reservation;

public record ReservationSaveRequest(String name, String date, String time) {

    public Reservation toReservation(final Long id) {
        return new Reservation(id, name, date, time);
    }
}
