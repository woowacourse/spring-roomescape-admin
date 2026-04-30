package roomescape.domain;

public class ReservationTime {

    private long id;
    private String startAt;

    public ReservationTime(final long id, final String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(final long id, final String startAt) {
        return new ReservationTime(id, startAt);
    }
}
