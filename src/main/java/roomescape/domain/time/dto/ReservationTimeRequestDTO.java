package roomescape.domain.time.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class ReservationTimeRequestDTO {
    @NotNull
    private final LocalTime startAt;

    public ReservationTimeRequestDTO(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return this.startAt;
    }
}
