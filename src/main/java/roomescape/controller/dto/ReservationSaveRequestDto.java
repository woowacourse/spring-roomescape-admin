package roomescape.controller.dto;

import roomescape.service.dto.ReservationSaveServiceDto;

public class ReservationSaveRequestDto {
    private String name;
    private String date;
    private String time;

    public ReservationSaveServiceDto toServiceDto() {
        return new ReservationSaveServiceDto(name, date, time);
    }
}
