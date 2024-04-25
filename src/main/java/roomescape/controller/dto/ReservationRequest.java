package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.ReservationCreationRequest;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationCreationRequest toCreationRequest() {
        return new ReservationCreationRequest(name, date, timeId);
    }
}
