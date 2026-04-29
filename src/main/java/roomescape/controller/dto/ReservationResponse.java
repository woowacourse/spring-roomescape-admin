package roomescape.controller.dto;

public record ReservationResponse(
        long id,
        String name,
        String date,
        ReservationTimeResponse time
) {
}
