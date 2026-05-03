package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class ReservationTimeRequestDTO {
    @JsonProperty("start_at")
    private LocalTime startAt;

    public ReservationTimeRequestDTO() {
    }

    public ReservationTimeRequestDTO(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
