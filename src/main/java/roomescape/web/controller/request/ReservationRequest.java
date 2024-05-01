package roomescape.web.controller.request;

import roomescape.core.service.request.ReservationRequestDto;

import java.time.LocalDate;
import java.util.Objects;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationRequest {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(timeId);
    }

    public ReservationRequestDto toDto() {
        return new ReservationRequestDto(name, date, timeId);
    }
}
