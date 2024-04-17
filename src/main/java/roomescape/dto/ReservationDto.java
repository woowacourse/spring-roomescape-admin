package roomescape.dto;

import roomescape.domain.reservation.Reservation;

public record ReservationDto(String name, String date, String time) {
    public Reservation toReservation(Long id) {
        return new Reservation(id, name, date, time);
    }
}
