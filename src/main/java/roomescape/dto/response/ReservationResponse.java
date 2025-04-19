package roomescape.dto.response;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
}
