package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private final Person person;
    private final ReservationDateTime reservationDateTime;

    public Reservation(Long id, Person person, ReservationDateTime reservationDateTime) {
        this.id = id;
        this.person = person;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(Person person, ReservationDateTime reservationDateTime) {
        this.person = person;
        this.reservationDateTime = reservationDateTime;
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
        return reservationDateTime.getDate();
    }

    public LocalTime getTime() {
        return reservationDateTime.getTime();
    }
}
