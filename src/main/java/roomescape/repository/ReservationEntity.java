package roomescape.repository;


public record ReservationEntity(
        Long id,
        String name,
        String date,
        ReservationTimeEntity timeEntity
) {
    public ReservationEntity initializeId(Long dataId) {
        return new ReservationEntity(
                dataId,
                this.name,
                this.date,
                this.timeEntity
        );
    }
}
