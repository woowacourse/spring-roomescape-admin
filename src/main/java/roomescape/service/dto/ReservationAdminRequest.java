package roomescape.service.dto;

import java.time.LocalDate;

public record ReservationAdminRequest(LocalDate date, long timeId, long themeId, long memberId) {
}
