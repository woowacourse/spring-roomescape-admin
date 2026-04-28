package roomescape;

public class Reservation {
    private final long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
