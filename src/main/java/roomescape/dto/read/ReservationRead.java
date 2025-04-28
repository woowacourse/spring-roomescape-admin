package roomescape.dto.read;

public record ReservationRead(
        Long reservationId,
        String name,
        String date,
        Long timeId,
        String timeValue
) {
}
