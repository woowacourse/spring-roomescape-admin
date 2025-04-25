package roomescape.presentation.dto;

import java.time.LocalDate;
import roomescape.business.domain.Reservation;
import roomescape.business.domain.Time;

public record ReservationRequest(
        String name, LocalDate date, Long timeId
) {

    public Reservation toDomain(final Time time) {
        return new Reservation(name, date, time);
    }
}
