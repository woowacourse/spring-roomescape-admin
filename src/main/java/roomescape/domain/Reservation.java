package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private final Person person;
    private final ReservationTime reservationTime;

    public Reservation(Long id, Person person, ReservationTime reservationTime) {
        this.id = id;
        this.person = person;
        this.reservationTime = reservationTime;
    }

    public Reservation(Person person, ReservationTime reservationTime) {
        this.person = person;
        this.reservationTime = reservationTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
