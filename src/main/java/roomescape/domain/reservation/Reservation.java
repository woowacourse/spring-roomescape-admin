package roomescape.domain.reservation;

import roomescape.domain.reservationtime.ReservationTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final Date date;
    private final ReservationTime reservationTime;

    public Reservation(Long id, String name, Date date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public static Reservation of(Long id, String name, String date, ReservationTime reservationTime) {
        return new Reservation(
                id,
                name,
                Date.from(date),
                reservationTime
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
