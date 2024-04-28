package roomescape.reservation.dto;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        String time
) {
}
