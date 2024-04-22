package roomescape.dto.reservation.response;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        String time
) {
}
