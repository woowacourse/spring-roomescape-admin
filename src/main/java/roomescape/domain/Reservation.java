package roomescape.domain;

public record Reservation(
        Long id,
        String name,
        String date,
        ReservationTime time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }
}
