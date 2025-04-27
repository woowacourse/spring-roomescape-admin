package roomescape.adapter.reservation;

import java.time.LocalDate;
import roomescape.usecase.reservation.ReservationInput;

public record ReservationRequestDto(LocalDate date, String name, Long timeId) {
    public ReservationInput toInput() {
        return new ReservationInput(date, name, timeId);
    }
}
