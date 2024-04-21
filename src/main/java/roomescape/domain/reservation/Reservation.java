package roomescape.domain.reservation;

public class Reservation {

    private final Long id;
    private final String name;
    private final Date date;
    private final Time time;

    private Reservation(Long id, String name, Date date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, String date, String time) {
        return new Reservation(
                id,
                name,
                Date.from(date),
                Time.from(time)
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
