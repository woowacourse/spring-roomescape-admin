package roomescape.reservation.entity;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.entity.ReservationTimeEntity;

public record ReservationEntity(
        long id,
        String name,
        LocalDate date,
        ReservationTimeEntity timeEntity
) {
    public ReservationEntity(final long id, final String name, final String date,
                             final ReservationTimeEntity timeEntity) {
        this(id, name, LocalDate.parse(date), timeEntity);
    }

    public Reservation toReservation() {
        return new Reservation(name, date, timeEntity.toReservationTime());
    }
}