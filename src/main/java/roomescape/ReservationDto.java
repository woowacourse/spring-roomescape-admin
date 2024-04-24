package roomescape;

public record ReservationDto(
        Long id,
        String name,
        String date,
        Long timeId
) {
    public ReservationDto toIdAssigned(final Long id) {
        return new ReservationDto(id, this.name, this.date, this.timeId);
    }

    public Reservation toEntity(final ReservationTime reservationTime) {
        return new Reservation(this.id, this.name, this.date, reservationTime);
    }

}
