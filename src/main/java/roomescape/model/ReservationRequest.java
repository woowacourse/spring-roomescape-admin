package roomescape.model;

public record ReservationRequest(
        String name,
        String date,
        Long timeId
) {
}
