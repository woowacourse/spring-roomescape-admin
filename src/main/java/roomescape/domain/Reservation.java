package roomescape.domain;

public class Reservation {

    private final long id;
    private final String name;
    private final String date;
    private final String time;

    private Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }

    public boolean hasId(long id) {
        return this.id == id;
    }
}
