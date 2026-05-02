package roomescape.service.dto;

import roomescape.controller.dto.ReservationCreateRequestDto;

public class ReservationCreateDto {
    private final String name;
    private final String date;
    private final Long timeId;

    public ReservationCreateDto(String name, String date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static ReservationCreateDto toDto(ReservationCreateRequestDto dto) {
        return new ReservationCreateDto(dto.getName(), dto.getDate(), dto.getTimeId());
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
