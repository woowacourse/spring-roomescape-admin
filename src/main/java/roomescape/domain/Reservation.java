package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final Name name;
    private final ReservationDate reservationDate;
    private final ReservationTime reservationTime;

    private Reservation(Long id, Name name, ReservationDate reservationDate, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this(id, new Name(name), new ReservationDate(date), new ReservationTime(time));
    }

    public Reservation(Long id, String name, String date, String time) {
        this(id, name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public Reservation(String name, String date, String time) {
        this(null, name, date, time);
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.name, reservation.reservationDate, reservation.reservationTime);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public ReservationDate getReservationDate() {
        return reservationDate;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
