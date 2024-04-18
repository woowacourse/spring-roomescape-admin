package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationRequest(String name, String date, String time) {

    public Reservation toInstance() {
        return new Reservation(name, date, time);
    }
}
