package roomescape.dto;

import java.time.LocalTime;

public class TimeRequestDto {
    private LocalTime startAt;

    public TimeRequestDto() {
    }

    public TimeRequestDto(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
