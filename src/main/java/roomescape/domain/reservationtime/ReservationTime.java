package roomescape.domain.reservationtime;

public class ReservationTime {

    private final Long id;
    private final StartAt startAt;

    public ReservationTime(Long id, StartAt startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public StartAt getStartAt() {
        return startAt;
    }
}
