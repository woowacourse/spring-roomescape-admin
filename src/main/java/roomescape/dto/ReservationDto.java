package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;

public record ReservationDto(String name, LocalDate date, LocalTime time) {
    public Reservation toReservation(Long id) {
        return new Reservation(id, name, date, time);
    }
}
