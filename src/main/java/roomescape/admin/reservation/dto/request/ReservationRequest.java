package roomescape.admin.reservation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {
}
