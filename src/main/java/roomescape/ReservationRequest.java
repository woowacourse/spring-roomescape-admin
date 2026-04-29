package roomescape;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }
}
