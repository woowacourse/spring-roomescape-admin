package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate reservationDate;
    private LocalTime reservationTime;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate reservationDate, LocalTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public Reservation(String name, LocalDate reservationDate, LocalTime reservationTime) {
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public static Reservation toEntity(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.reservationDate, reservation.reservationTime);
    }

    public Long getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
    }
}
