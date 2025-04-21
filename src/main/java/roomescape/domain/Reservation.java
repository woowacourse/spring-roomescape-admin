package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private Long id;
    private final Person person;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(Long id, Person person, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.person = person;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(Person person, LocalDate date, ReservationTime reservationTime) {
        this.person = person;
        this.date = date;
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
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public long getTimeId() {
        return reservationTime.getId();
    }
}
