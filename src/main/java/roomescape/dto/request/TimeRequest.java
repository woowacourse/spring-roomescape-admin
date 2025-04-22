package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record TimeRequest(
        @NotBlank
        @Pattern(regexp = "\\d{2}:\\d{2}")
        String startAt
) {
    public ReservationTime toDomain() {
        return ReservationTime.withoutId(LocalTime.parse(startAt));
    }
}
