package roomescape.reservation.controller.request;

import java.time.LocalDate;
import roomescape.reservation.model.Reservation;
import roomescape.reservationTime.controller.response.ReservationTimeResponse;
import roomescape.reservationTime.model.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation toEntity(final Long id, final ReservationTimeResponse timeResponse) {
        return new Reservation(
                id,
                name,
                date,
                new ReservationTime(
                        timeResponse.id(),
                        timeResponse.startAt()
                )
        );
    }
}
