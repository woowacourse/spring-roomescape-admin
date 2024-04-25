package roomescape.reservation.dto;

public record ReservationRequestDto(
        String name,
        String date,
        String time
) {
}
