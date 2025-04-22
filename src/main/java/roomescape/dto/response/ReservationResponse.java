package roomescape.dto.response;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        TimeResponse time
) {
}
