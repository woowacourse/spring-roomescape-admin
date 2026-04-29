package roomescape.reservation.dto;

public record ReservationCreateRequestDto(
        String name,
        String date,
        Long timeId
) {
}
