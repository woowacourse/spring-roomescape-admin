package roomescape.reservation;

import roomescape.time.Time;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation() {
    }

    public Reservation(final Long id, final String name, final String date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
