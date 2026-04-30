package roomescape.domain;

public class ReservationTime {

    private final long id;
    private final String startAt;

    private ReservationTime(final long id, final String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(final long id, final String startAt) {
        return new ReservationTime(id, startAt);
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
