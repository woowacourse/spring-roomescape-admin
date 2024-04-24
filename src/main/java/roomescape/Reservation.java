package roomescape;

public record Reservation(
        Long id,
        String name,
        String date,
        ReservationTime time) {

    public Reservation toIdAssigned(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

}
