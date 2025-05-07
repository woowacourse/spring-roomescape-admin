package roomescape.domain;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation(final Long id, final String name, final String date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final String date, final Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation() {
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

    public void setId(final Long id) {
        this.id = id;
    }
}
