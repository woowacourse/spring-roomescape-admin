package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(

        @NotNull
        LocalDate date,

        @NotNull
        String name,

        @NotNull
        long timeId
) {
    public Reservation fromEntity() {
        final ReservationTime reservationTime = new ReservationTime(timeId, null);
        return new Reservation(null, name, date, reservationTime);
    }
}
