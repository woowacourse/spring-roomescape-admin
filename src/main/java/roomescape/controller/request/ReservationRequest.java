package roomescape.controller.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(@NotBlank String name, @Future LocalDate date, @NotNull Long timeId) {

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
