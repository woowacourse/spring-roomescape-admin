package roomescape.dto;

public record CreateReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
}
