package roomescape.domain;

import java.time.LocalDate;


public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) throws NullPointerException {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, ReservationTime time) {
        if(name == null || date == null || time == null) {
            throw new NullPointerException();
        }
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

    public ReservationTime getTime() {
        return time;
    }

    public Long getTimeId() {
        return time.getId();
    }
}
