package roomescape.dto;

import roomescape.ReservationInfo;

public class ReservationInfoDto {

    private final String name;
    private final String date;
    private final String time;

    public ReservationInfoDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationInfo toEntity(Long id) {
        return new ReservationInfo(id, name, date, time);
    }
}
