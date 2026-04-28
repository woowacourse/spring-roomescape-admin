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
}
