package roomescape.dto.app;

import java.time.LocalDate;

public record ReservationAppRequest(LocalDate date, String name, Long timeId) {
}
