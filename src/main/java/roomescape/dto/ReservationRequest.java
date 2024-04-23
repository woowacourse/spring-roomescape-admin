package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationRequest(String name, String date, String time) {

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
