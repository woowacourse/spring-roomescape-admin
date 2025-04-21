package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.Reservation;

public record CreateReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation(final long id) {
        return new Reservation(id, name, date, time);
    }
}
