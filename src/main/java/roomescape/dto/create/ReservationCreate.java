package roomescape.dto.create;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReservationCreate(
        String name,
        String date,
        Long timeId
) {
    public Reservation toReservation(final long id, final ReservationTime time) {
        return new Reservation(id, name, LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), time);
    }
}
