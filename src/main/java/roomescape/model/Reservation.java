package roomescape.model;

public record Reservation(
        Long id,
        String name,
        String date,
        Long timeId
) {
    public Reservation toIdAssigned(final Long id) {
        return new Reservation(id, this.name, this.date, this.timeId);
    }

    public ReservationInfo toReservationInfo(final ReservationTime reservationTime) {
        return new ReservationInfo(this.id, this.name, this.date, reservationTime);
    }

}
