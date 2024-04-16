package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record CreateReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation(final Long id) {
       return new Reservation(id, name, date, time);
    }
}
