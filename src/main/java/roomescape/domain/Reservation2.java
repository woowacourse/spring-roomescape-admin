package roomescape.domain;

public record Reservation2(
        Long id,
        String name,
        String date,
        ReservationTime time) {

    public Reservation2 toEntity(Long id) {
        return new Reservation2(id, this.name, this.date, this.time);
    }
}
