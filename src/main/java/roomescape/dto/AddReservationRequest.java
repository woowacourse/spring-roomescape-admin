package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record AddReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation(Long id) {
        return new Reservation(id, name, date, time);
    }
}
