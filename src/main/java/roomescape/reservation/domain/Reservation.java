package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final ReserverName reserverName;
    private final ReservationDateTime reservationDateTime;

    public Reservation(Long id, String reserverName, LocalDateTime dateTime) {
        this.id = id;
        this.reserverName = new ReserverName(reserverName);
        this.reservationDateTime = new ReservationDateTime(dateTime, LocalDateTime.now());
    }

    public Reservation(String reserverName, LocalDateTime dateTime) {
        this(null, reserverName, dateTime);
    }

    public Long getId() {
        return id;
    }

    public String getReserverName() {
        return reserverName.getName();
    }

    public LocalDateTime getDateTime() {
        return reservationDateTime.getDateTime();
    }

    public LocalDate getDate() {
        return reservationDateTime.getDate();
    }

    public LocalTime getTime() {
        return reservationDateTime.getTime();
    }
}
