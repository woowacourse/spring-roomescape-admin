package roomescape.domain;

public class Reservation {
    private long id;
    private final String name;
    private final String date;
    private final String time;

    private Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
