package roomescape.dto;

import roomescape.entity.Reservation;

public record ReservationRequest(String name, String date, String time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
