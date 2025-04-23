package roomescape.model;

public class ReservationTime {
    private final Long id;
    private final String start_at;

    public ReservationTime(Long id, String start_at) {
        this.id = id;
        this.start_at = start_at;
    }

    public Long getId() {
        return id;
    }

    public String getStart_at() {
        return start_at;
    }

}
