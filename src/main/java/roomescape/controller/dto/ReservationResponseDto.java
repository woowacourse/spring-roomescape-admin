package roomescape.controller.dto;

import roomescape.service.dto.ReservationDto;

public class ReservationResponseDto {
    private final long id;
    private final String name;
    private final String date;
    private final long timeId;

    public ReservationResponseDto(long id, String name, String date, long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static ReservationResponseDto toDto(ReservationDto dto) {
        return new ReservationResponseDto(dto.getId(), dto.getName(), dto.getDate(), dto.getTimeId());
    }

    public long getId() {
        return id;
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
