package roomescape.domain;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toEntity(Long id, TimeSlot time) {
        return new Reservation(id, name, date, time);
    }
}
