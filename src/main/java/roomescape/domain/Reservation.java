package roomescape.domain;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(final String name, final String date, final ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final Long id, final String name, final String date, final ReservationTime time) {
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

    public ReservationTime getReservationTime() {
        return time;
    }
}
