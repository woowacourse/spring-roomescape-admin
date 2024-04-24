package roomescape.model;

public record ReservationInfo(
        Long id,
        String name,
        String date,
        Long timeId
) {
    public ReservationInfo toIdAssigned(final Long id) {
        return new ReservationInfo(id, this.name, this.date, this.timeId);
    }

    public Reservation toReservation(final ReservationTime reservationTime) {
        return new Reservation(this.id, this.name, this.date, reservationTime);
    }

}
