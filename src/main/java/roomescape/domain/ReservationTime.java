package roomescape.domain;

public class ReservationTime {

    private Long id;
    private String startAt;

    public ReservationTime(String startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public boolean isSameReservationTime(Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
