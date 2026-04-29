package roomescape.domain;

public record Reservation(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
    public static Reservation constructWithNoId(String name, String date, ReservationTime time) {
        return new Reservation(null, name, date, time);
    }
}
