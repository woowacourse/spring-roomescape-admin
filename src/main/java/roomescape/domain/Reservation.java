package roomescape.domain;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;

    private Reservation(
            Long id,
            String name,
            String date,
            ReservationTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(
            String name,
            String date
    ) {
        return new Reservation(
                null,
                name,
                date,
                null
        );
    }

    public static Reservation retrieve(
            long id,
            String name,
            String date,
            ReservationTime time
    ) {
        return new Reservation(
                id,
                name,
                date,
                time
        );
    }

    public Reservation withId(long id) {
        return new Reservation(
                id,
                this.name,
                this.date,
                this.time
        );
    }

    public Reservation withTime(ReservationTime time) {
        return new Reservation(
                this.id,
                this.name,
                this.date,
                time
        );
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

    public ReservationTime getTime() {
        return time;
    }
}
