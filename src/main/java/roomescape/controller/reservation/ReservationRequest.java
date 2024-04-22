package roomescape.controller.reservation;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
}
