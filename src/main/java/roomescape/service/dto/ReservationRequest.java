package roomescape.service.dto;

import java.time.LocalDate;

public record ReservationRequest(LocalDate date, long timeId, long themeId, String status) {

    public ReservationRequest(ReservationAdminRequest adminRequest) {
        this(adminRequest.date(), adminRequest.timeId(), adminRequest.themeId(), adminRequest.status());
    }
}
