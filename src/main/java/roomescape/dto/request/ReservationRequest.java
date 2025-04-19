package roomescape.dto.request;

public record ReservationRequest(
        String date,
        String name,
        String time
) {
}
