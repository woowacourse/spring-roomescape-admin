package roomescape.reservation.dto.request;

public record ReservationRequestDto(
        String name,
        String date,
        Long timeId
) {
}
