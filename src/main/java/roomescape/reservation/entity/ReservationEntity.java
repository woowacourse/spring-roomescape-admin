package roomescape.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationEntity(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public ReservationEntity(final long id, final String name, final String date, final String time) {
        this(id, name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}