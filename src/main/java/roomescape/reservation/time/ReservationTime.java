package roomescape.reservation.time;

public class ReservationTime {
    private final Long id;
    private final String startAt;

    private ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(Long id, String startAt) {
        return new ReservationTime(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
