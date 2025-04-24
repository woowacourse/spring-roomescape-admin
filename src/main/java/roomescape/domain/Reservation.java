package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private final ReserverName name;
    private final ReservationDateTime dateTime;

    public Reservation(final Long id, final ReserverName name, final ReservationDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation(final ReserverName name, final ReservationDateTime dateTime) {
        this(null, name, dateTime);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return dateTime.getDate();
    }

    public LocalTime getTime() {
        return dateTime.getTime();
    }

    public Long getTimeId() {
        return dateTime.getTimeId();
    }

    public void setId(final long id) {
        this.id = id;
    }
}
