package roomescape;

public record ReservationRequest(
        String name,
        String date,
        Long timeId
) {
}
