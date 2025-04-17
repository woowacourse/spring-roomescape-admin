package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public record Reservation(long id, Person person, ReservationTime reservationTime) {

    private static final AtomicLong reservationIndex = new AtomicLong(1);

    public Reservation(Person person, ReservationTime reservationTime) {
        this(reservationIndex.getAndIncrement(), person, reservationTime);
    }

    public String getPersonName() {
        return person.name();
    }

    public LocalDate getDate() {
        return reservationTime.getDate();
    }

    public LocalTime getTime() {
        return reservationTime.getTime();
    }
}
