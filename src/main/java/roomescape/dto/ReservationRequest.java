package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {


    public Reservation toReservation() {
        return new Reservation(toPerson(), toReservationTime());
    }

    private ReservationTime toReservationTime() {
        return new ReservationTime(LocalDateTime.of(date, time));
    }

    private Person toPerson() {
        return new Person(name);
    }
}
