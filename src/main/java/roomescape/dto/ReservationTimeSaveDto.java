package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeSaveDto {

    private LocalTime startAt;

    public ReservationTimeSaveDto(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTimeSaveDto() {
    }

    public static ReservationTimeSaveDto from(ReservationTimeRequestDto requestDto) {
        return new ReservationTimeSaveDto(requestDto.getStartAt());
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
