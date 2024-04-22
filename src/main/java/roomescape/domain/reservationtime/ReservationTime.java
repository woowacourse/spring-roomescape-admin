package roomescape.domain.reservationtime;

public class ReservationTime {

    private final Long id;
    private final StartAt startAt;

    private ReservationTime(Long id, StartAt startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(Long id, String startAt) {
        return new ReservationTime(
                id,
                StartAt.from(startAt)
        );
    }

    public Long getId() {
        return id;
    }

    public StartAt getStartAt() {
        return startAt;
    }
}
