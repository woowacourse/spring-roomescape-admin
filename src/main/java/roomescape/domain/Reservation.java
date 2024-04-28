package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(long id, String name, String date, ReservationTime time) {
        this(id, name, LocalDate.parse(date), time);
    }

    public Reservation(long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean hasSameTimeId(long timeId) {
        return time.getId() == timeId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
