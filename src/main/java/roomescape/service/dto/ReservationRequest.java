package roomescape.service.dto;

import java.time.LocalDate;

public record ReservationRequest(LocalDate date, long timeId, long themeId) {

    public ReservationRequest(ReservationAdminRequest adminRequest) {
        this(adminRequest.date(), adminRequest.timeId(), adminRequest.themeId());
    }
}
