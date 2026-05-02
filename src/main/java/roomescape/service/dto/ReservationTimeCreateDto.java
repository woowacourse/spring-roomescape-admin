package roomescape.service.dto;

import roomescape.controller.dto.ReservationTimeCreateRequestDto;

public class ReservationTimeCreateDto {
    private final String startAt;

    public ReservationTimeCreateDto(String startAt) {
        this.startAt = startAt;
    }

    public static ReservationTimeCreateDto toDto(ReservationTimeCreateRequestDto dto) {
        return new ReservationTimeCreateDto(dto.getStartAt());
    }

    public String getStartAt() {
        return startAt;
    }
}
