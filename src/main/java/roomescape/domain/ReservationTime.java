package roomescape.domain;

public class ReservationTime {

    private final Long id;
    private final String startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(String startAt) {
        this(null, startAt);
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
