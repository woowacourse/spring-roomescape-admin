package roomescape.domain;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Long timeId;
    private ReservationTime time;

    public Reservation() {

    }

    public Reservation(String name, String date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
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

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Long getTimeId() {
        return timeId;
    }
}
