package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private long id;
    private Person person;
    private LocalDate date;
    private ReservationTime reservationTime;

    public Reservation(final long id, final Person person, final LocalDate date,
                       final ReservationTime reservationTime) {
        this.id = id;
        this.person = person;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(final Person person, final LocalDate date, final ReservationTime reservationTime) {
        this.id = 0L;
        this.person = person;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(final long id, final Reservation reservation) {
        this.id = id;
        this.person = reservation.getPerson();
        this.reservationTime = reservation.getReservationTime();
    }

    public Reservation(final long id, final Reservation reservation, final ReservationTime reservationTime) {
        this.id = id;
        this.person = reservation.getPerson();
        this.date = reservation.getDate();
        this.reservationTime = reservationTime;
    }

    public Reservation(final Person person, final LocalDate date) {
        this.person = person;
        this.date = date;
    }

    public String getPersonName() {
        return person.name();
    }

    public Person getPerson() {
        return person;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getStartAt() {
        return reservationTime.getStartAt();
    }

    public long getId() {
        return id;
    }

    public boolean isSameId(long id) {
        return this.id == id;
    }
}
