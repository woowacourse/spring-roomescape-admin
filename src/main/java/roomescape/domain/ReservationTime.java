package roomescape.domain;

public class ReservationTime {
    private long id;
    private String startAt;

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
