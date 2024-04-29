package roomescape.model;

public record ReservationDto(
        Long id,
        String name,
        String date,
        Long timeId,
        String startAt
) {
}
