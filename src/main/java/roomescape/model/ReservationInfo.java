package roomescape.model;

public record ReservationInfo(
        Long id,
        String name,
        String date,
        ReservationTime time) {
}
