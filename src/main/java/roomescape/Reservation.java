package roomescape;

public record Reservation(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
}
