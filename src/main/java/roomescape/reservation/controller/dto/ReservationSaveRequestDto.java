package roomescape.reservation.controller.dto;

import roomescape.reservation.service.dto.ReservationSaveServiceDto;

public class ReservationSaveRequestDto {
    private String name;
    private String date;
    private Long timeId;

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public ReservationSaveServiceDto toServiceDto() {
        return new ReservationSaveServiceDto(name, date, timeId);
    }
}
