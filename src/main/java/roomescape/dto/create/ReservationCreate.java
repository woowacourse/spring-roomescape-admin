package roomescape.dto.create;

public record ReservationCreate(
        String name,
        String date,
        Long timeId
) {
}
