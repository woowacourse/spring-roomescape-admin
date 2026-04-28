package roomescape.domain;

public class ReservationTime {

    private final Long id;
    private final String startAt;

    private ReservationTime(
            Long id,
            String startAt
    ) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public static ReservationTime create(
            String startAt
    ) {
        return new ReservationTime(
                null,
                startAt
        );
    }

    public ReservationTime with(long id) {
        return new ReservationTime(
                id,
                this.startAt
        );
    }
}
