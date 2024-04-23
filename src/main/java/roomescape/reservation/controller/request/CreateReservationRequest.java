package roomescape.reservation.controller.request;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

public record CreateReservationRequest(LocalDate date, String name, Long timeId) {

    public Reservation toDomain(final ReservationTime reservationTime) {
        return new Reservation(
                null,
                this.name,
                this.date,
                reservationTime.getId(),
                reservationTime.getTime()
        );
    }
}
