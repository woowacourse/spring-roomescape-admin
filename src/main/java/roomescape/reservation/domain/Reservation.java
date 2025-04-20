package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final ReserverName reserverName;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String reserverName, LocalDateTime dateTime) {
        this.id = id;
        this.reserverName = new ReserverName(reserverName);
        this.dateTime = dateTime;
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
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
