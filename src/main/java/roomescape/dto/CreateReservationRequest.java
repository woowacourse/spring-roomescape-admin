package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record CreateReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
