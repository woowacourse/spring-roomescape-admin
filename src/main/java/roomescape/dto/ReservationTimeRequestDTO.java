package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class ReservationTimeRequestDTO {
    private Long id;

    @JsonProperty("start_at")
    private LocalTime startAt;

    public ReservationTimeRequestDTO() {
    }

    public ReservationTimeRequestDTO(LocalTime startAt, Long id) {
        this.startAt = startAt;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
