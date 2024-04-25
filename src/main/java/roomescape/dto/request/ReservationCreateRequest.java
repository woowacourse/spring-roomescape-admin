package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import roomescape.service.request.ReservationCreateRequestInService;

public record ReservationCreateRequest(
        @NotNull String name,
        @NotNull @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String date,
        long timeId) {

    public ReservationCreateRequestInService convert() {
        return new ReservationCreateRequestInService(name, date, timeId);
    }
}
