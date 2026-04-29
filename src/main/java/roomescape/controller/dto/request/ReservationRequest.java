package roomescape.controller.dto.request;


public record ReservationRequest(
        String name,
        String date,
        String time
) {
}
