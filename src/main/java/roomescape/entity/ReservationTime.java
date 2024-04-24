package roomescape.entity;

public class ReservationTime {

    private final long id;
    private final String startAt;

    public ReservationTime(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
