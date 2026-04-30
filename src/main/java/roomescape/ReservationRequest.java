package roomescape;

public record ReservationRequest(
        String date,
        String name,
        Long timeId
) {
    public Reservation toEntity(Long id, ReservationTime reservationTime) {
        return new Reservation(id, this.name, this.date, reservationTime);
    }
}
