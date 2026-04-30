package roomescape.reservation;

import roomescape.reservationtime.ReservationTime;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime reservationTime;

    public Reservation(Long id, String name, String date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.getReservationTime());
    }
}
