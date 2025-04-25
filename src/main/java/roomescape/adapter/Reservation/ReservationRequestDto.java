package roomescape.adapter.Reservation;

import java.time.LocalDate;
import roomescape.usecase.Reservation.ReservationInput;

public record ReservationRequestDto(LocalDate date, String name, Long timeId) {
    public ReservationInput toInput() {
        return new ReservationInput(date, name, timeId);
    }
}
