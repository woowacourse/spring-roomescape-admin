package roomescape.domain;

public class Reservation {

    private final Long id;
    private final Name name;
    private final String date;
    private final ReservationTime time;

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = new Name(name);
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date) {
        this(null, name, date, null);
    }

    public Reservation(String name, String date, ReservationTime time) {
        this(null, name, date, time);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name=" + name +
                ", date='" + date + '\'' +
                ", time=" + time +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getNameValue() {
        return name.getName();
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
