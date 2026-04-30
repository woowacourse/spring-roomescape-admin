package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeCreateReqDto {

    private LocalTime startAt;

    public ReservationTimeCreateReqDto(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
