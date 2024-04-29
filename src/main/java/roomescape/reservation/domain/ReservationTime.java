package roomescape.reservation.domain;

public class ReservationTime {
    private Long id;
    private String startAt;

    public ReservationTime(final String startAt) {
        this(null, startAt);
    }

    public ReservationTime(final Long id) {
        this(id, null);
    }

    public ReservationTime(final long id, final ReservationTime reservationTime) {
        this(id, reservationTime.getStartAt());
    }

    public ReservationTime(final Long id, final String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
