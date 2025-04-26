package roomescape.dto.request;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.valid.annotation.NotNull;

public record ReservationRequest(

        @NotNull
        LocalDate date,

        @NotNull
        String name,

        @NotNull
        Long timeId
) {
    public Reservation fromEntity() {
        final ReservationTime reservationTime = new ReservationTime(timeId, null);
        return new Reservation(null, name, date, reservationTime);
    }
}
