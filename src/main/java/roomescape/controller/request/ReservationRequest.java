package roomescape.controller.request;

import java.time.LocalDate;

public record ReservationRequest(LocalDate date, String name, long timeId) {
}
