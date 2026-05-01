package roomescape.repository;

public record ReservationTimeEntity(
        Long id,
        String startAt
) {
    public ReservationTimeEntity initializeWithId(Long id) {
        return new ReservationTimeEntity(id, this.startAt);
    }
}
