package roomescape.controller.dto;

import roomescape.service.dto.ReservationSaveServiceDto;

public class ReservationSaveRequestDto {
    private String name;
    private String date;
    private String time;

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ReservationSaveServiceDto toServiceDto() {
        return new ReservationSaveServiceDto(name, date, time);
    }
}
