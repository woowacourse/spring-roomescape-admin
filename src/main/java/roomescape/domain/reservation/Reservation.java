package roomescape.domain.reservation;

import roomescape.domain.reservationtime.ReservationTime;

public class Reservation {

    private final Long id;
    private final Name name;
    private final Date date;
    private final ReservationTime reservationTime;

    private Reservation(Long id, Name name, Date date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public static Reservation of(Long id, String name, String date, ReservationTime reservationTime) {
        return new Reservation(
                id,
                new Name(name),
                Date.from(date),
                reservationTime
        );
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
