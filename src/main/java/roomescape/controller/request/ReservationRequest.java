package roomescape.controller.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReserverName;

public record ReservationRequest(@NotBlank String name, @Future LocalDate date, @NotNull Long timeId) {

    public Reservation toEntity(final ReservationTimeResponse reservationTimeResponse) {
        return new Reservation(
                new ReserverName(name),
                new ReservationDate(date),
                new ReservationTime(
                        reservationTimeResponse.id(),
                        reservationTimeResponse.startAt()
                )
        );
    }
}
