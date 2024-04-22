package roomescape.admin.reservation.dto.request;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
}
