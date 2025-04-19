package roomescape.reservation.controller.request;

public record ReservationCreateRequest(
        String name,
        String date,
        String time
) {
}
