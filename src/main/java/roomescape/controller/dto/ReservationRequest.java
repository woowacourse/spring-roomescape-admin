package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.Person;
import roomescape.domain.Reservation;

public record ReservationRequest(String name, LocalDate date, long timeId) {

    public Reservation toReservation() {
        return new Reservation(toPerson(), date);
    }

    private Person toPerson() {
        return new Person(name);
    }
}
