package roomescape.model;

public record ReservationTime(
        Long id,
        String startAt
) {

    public ReservationTime toIdAssigned(final Long id) {
        return new ReservationTime(id, this.startAt);
    }
}
