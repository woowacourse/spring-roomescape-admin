package roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record TimeRequest(
        @JsonFormat(pattern = "HH:mm")
        @NotNull
        LocalTime startAt
) {
    public ReservationTime toDomain() {
        return ReservationTime.withoutId(startAt);
    }
}
