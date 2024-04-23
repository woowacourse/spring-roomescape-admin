package roomescape.reservation.controller.request;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.NewReservationTime;

public record CreateReservationRequest(LocalDate date, String name, Long timeId) {

    public Reservation toDomain(final NewReservationTime newReservationTime) {
        return new Reservation(
                null,
                this.name,
                this.date,
                newReservationTime.getId(),
                newReservationTime.getTime()
        );
    }
}
