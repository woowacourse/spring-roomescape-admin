package roomescape.service.dto;

import java.time.LocalDate;
import roomescape.domain.Person;
import roomescape.domain.Reservation;

public record ReservationRequest(String name, LocalDate date, long timeId) {

    public Reservation toReservation() {
        return new Reservation(new Person(name), date);
    }
}
