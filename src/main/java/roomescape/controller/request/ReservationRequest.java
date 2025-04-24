package roomescape.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReserverName;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation toEntity(final ReservationTimeResponse reservationTimeResponse) {
        return new Reservation(
                new ReserverName(name),
                new ReservationDateTime(
                        new ReservationDate(date),
                        new ReservationTime(
                                reservationTimeResponse.id(),
                                LocalTime.parse(reservationTimeResponse.startAt())
                        )
                )
        );
    }
}
