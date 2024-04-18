package roomescape;

public record SaveReservationRequest(String name, String date, String time) {
    public static Reservation toEntity(Long id, SaveReservationRequest request) {
        return new Reservation(id, request.name(), request.date(), request.time());
    }
}
