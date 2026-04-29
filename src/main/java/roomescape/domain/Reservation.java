package roomescape.domain;

public record Reservation(long id, String name, String date, String time) {
    public Reservation update(long id) {
        return new Reservation(id, name, date, time);
    }
}
