package roomescape.service.dto;

import java.time.LocalDate;

public record ReservationRequest(LocalDate date, long timeId, long themeId) {
}
