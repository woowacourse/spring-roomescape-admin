package roomescape.domain;

public record Reservation(
        Long id,
        String name,
        String date,
        ReservationTime time) {

    public Reservation(String name, String date, ReservationTime time) {
        this(null, name, date, time);
    }

}
