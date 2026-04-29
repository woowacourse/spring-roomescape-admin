package roomescape.control.dto;

public record ReservationCreateRequest(
        String name,
        String date,
        String time
) {
}
