package roomescape.reservationtime;

public class ReservationTime {
    private long id;
    private String start_at;

    public ReservationTime(long id, String start_at) {
        this.id = id;
        this.start_at = start_at;
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return start_at;
    }

    public static ReservationTime toEntity(ReservationTime reservationTime, Long id) {
        return new ReservationTime(id, reservationTime.start_at);
    }
}
