package roomescape.dto.reservation.request;

public record ReservationRequestDto(
        String name,
        String date,
        String time
) {
}
