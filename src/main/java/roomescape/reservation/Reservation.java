package roomescape.reservation;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private Long time_id;

    public Reservation(long id, String name, String date, Long time_id) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time_id = time_id;
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

    public Long getTimeId() {
        return time_id;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time_id);
    }
}
