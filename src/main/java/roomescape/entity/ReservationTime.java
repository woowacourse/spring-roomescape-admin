package roomescape.entity;

public class ReservationTime {
    private final Long id;
    private final String startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }

    public Long getId() {
        return id;
    }
}
