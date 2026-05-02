package roomescape.repository.dto;

import roomescape.service.dto.ReservationTimeCreateDto;

public class ReservationTimeSaveDto {
    private final String startAt;

    public ReservationTimeSaveDto(String startAt) {
        this.startAt = startAt;
    }

    public static ReservationTimeSaveDto toDto(ReservationTimeCreateDto dto) {
        return new ReservationTimeSaveDto(dto.getStartAt());
    }

    public String getStartAt() {
        return startAt;
    }
}
