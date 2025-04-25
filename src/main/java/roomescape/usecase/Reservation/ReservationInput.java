package roomescape.usecase.Reservation;

import java.time.LocalDate;

public record ReservationInput(LocalDate date, String name, Long timeId) {
}
