package roomescape.domain;

public class ReservationTime {

    private final Long id;
    private final String date;

    public ReservationTime(
            Long id,
            String date
    ) {
        this.id = id;
        this.date = date;
    }
}
