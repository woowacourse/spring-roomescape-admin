package roomescape.domain;

public class ReservationTime {

    private final long id;
    private final String startAt;

    private ReservationTime(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(long id, String startAt) {
        return new ReservationTime(id, startAt);
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
