package roomescape.repository;


public record ReservationEntity(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationEntity constructWithNoId() {
        return null;
    }

    public ReservationEntity initializeId(Long dataId) {
        return new ReservationEntity(
                dataId,
                this.name,
                this.date,
                this.time
        );
    }
}
