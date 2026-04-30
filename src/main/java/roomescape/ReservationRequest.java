package roomescape;

import java.time.LocalDate;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public Reservation toEntity(Long id, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }
}
