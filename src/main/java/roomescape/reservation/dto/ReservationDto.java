package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.Reservation;

public record ReservationDto(String name, LocalDate date, LocalTime time) {
    public Reservation createReservation() {
        return new Reservation(name, date, time);
    }
}
