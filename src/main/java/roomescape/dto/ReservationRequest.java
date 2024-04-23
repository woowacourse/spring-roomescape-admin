package roomescape.dto;

import roomescape.entity.Reservation;

public record ReservationRequest(String name, String date, String time) {

    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
