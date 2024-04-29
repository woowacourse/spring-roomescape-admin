package roomescape.reservation.dto.response;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        ReservationTimeResponseDto time
) {
}
