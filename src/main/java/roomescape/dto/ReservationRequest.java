package roomescape.dto;

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
}
