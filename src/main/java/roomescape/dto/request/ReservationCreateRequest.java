package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationCreateRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull Long timeId
) {
    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
