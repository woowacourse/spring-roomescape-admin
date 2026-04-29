package roomescape.reservation;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    private Reservation(Long id, String name, LocalDate date, Long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static Reservation of(Long id, String name, LocalDate date, Long timeId) {
        return new Reservation(id, name, date, timeId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

}
