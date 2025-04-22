package roomescape.controller.request;

import java.time.LocalDate;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

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
