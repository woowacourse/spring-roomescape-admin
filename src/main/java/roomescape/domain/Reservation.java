package roomescape.domain;

public record Reservation(long id, String name, String date, ReservationTime time) {
}
