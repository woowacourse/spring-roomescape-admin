package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private final ReservationName name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final ReservationName name, final ReservationDate date,
                       final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final ReservationName name, final ReservationDate date, final ReservationTime time) {
        this(null, name, date, time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date.getDate();
    }

    public LocalTime getTime() {
        return time.getStartAt();
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Long getTimeId() {
        return time.getId();
    }
}
