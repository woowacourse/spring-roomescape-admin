package roomescape.dto;

import roomescape.Reservation;

public record RequestReservation(String name, String date, String time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
