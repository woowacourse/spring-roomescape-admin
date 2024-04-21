package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.user.UserName;

public class Reservation {
    private final Long id;
    private final UserName name;
    private final ReservationDateTime reservationDateTime;

    public Reservation(String name, ReservationDateTime dateTime) {
        this(null, name, dateTime);
    }

    public Reservation(Long id, String name, ReservationDateTime dateTime) {
        this(id, new UserName(name), dateTime);
    }

    private Reservation(Long id, UserName name, ReservationDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.reservationDateTime = dateTime;
    }

    public Reservation updateId(Long id) {
        return new Reservation(id, name, reservationDateTime);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime.getDateTime();
    }

    public LocalDate getReservationDate() {
        return reservationDateTime.toLocalDate();
    }

    public LocalTime getReservationTime() {
        return reservationDateTime.toLocalTime();
    }

    public boolean isSameReservationDateTime(ReservationDateTime reservationDateTime) {
        return this.reservationDateTime.equals(reservationDateTime);
    }
}
