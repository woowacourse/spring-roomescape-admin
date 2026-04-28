package roomescape.domain;

public record Reservation(
        Long id,
        String name,
        String date,
        String time
) {
    public static Reservation constructWithNoId(String name, String date, String time) {
        return new Reservation(null, name, date, time);
    }
}
