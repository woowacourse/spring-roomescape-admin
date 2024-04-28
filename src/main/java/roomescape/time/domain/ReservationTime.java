package roomescape.time.domain;

public class ReservationTime {
    private Long id;
    private String startAt;

    public ReservationTime(final String startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(final long id, final ReservationTime reservationTime) {
        this.id = id;
        this.startAt = reservationTime.startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
