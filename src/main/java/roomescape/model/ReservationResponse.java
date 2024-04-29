package roomescape.model;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTimeResponse time) {
}
