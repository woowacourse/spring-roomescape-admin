package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeRequestDto {

    private LocalTime startAt;

    public ReservationTimeRequestDto(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTimeRequestDto() {
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
