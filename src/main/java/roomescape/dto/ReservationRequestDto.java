package roomescape.dto;

import roomescape.entity.Reservation;

public class ReservationRequestDto {

    private String name;
    private String date;
    private String time;

    public ReservationRequestDto() {
    }

    public Reservation toEntity(long id) {
        return new Reservation(id, name, date, time);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
